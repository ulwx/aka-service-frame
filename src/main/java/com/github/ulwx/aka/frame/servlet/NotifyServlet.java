package com.github.ulwx.aka.frame.servlet;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.Exchange;
import com.github.ulwx.aka.frame.protocol.req.ICallBack;
import com.github.ulwx.aka.frame.protocol.req.ICallBack.CallBackType;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogNotifyDao;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogNotifyMoreDao;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyMoreBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.IError;
import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.support.*;
import com.github.ulwx.aka.frame.utils.NetTools;
import com.github.ulwx.aka.frame.utils.UIFrameAppConfig;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.*;
import org.apache.log4j.Logger;
import org.slf4j.MDC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 接口servlet
 * 
 */
@MultipartConfig
@WebServlet(name = "NotifyServlet", urlPatterns = "/notify/intf.do")
public class NotifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NotifyServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		//InterLogNotifyBean insertNotify = new InterLogNotifyBean();
		CallbackBean callbackBean = null;
		String responseType = "";
		long start = System.currentTimeMillis();
		AkaFrameProperties akaFrameProperties=BeanGet.getBean(AkaFrameProperties.class,request );
		UIFrameAppConfig uIFrameAppConfig=BeanGet.getBean(UIFrameAppConfig.class,request );
		try {
			request.setCharacterEncoding("UTF-8");
			logger.info("callback request==========+++++++++++======");

			String queryStr = NetUtils.urlMapToQueryStr(request.getParameterMap(), "utf-8");
			logger.info("queryStr=" + queryStr);
			logger.info("request=" + ObjectUtils.toJsonString(request.getParameterMap()));
			logger.info("request URI=" + request.getRequestURI());
			String reqCodeArgName = StringUtils.trim(akaFrameProperties.getNotifyRequest().getReqcodeArgname());
			if(reqCodeArgName.isEmpty()) {
				reqCodeArgName=UiFrameConstants.NOTIFY_REQ_CODE_ARGNAME;
			}
			// reqCode区分接口逻辑，[ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace]
			// v1@trade@jjs@1@CUSTOMER_REGISTER@@intf
			// callType：1-异步回调 2-网关回调 3-本地窗体
			String reqCode = StringUtils.trim(request.getParameter(reqCodeArgName));
			logger.debug("reqCode=" + reqCode + ",reqCodeArgName=" + reqCodeArgName);
			ReqCodeParm notifyReqCode = ReqCodeParm.parse(reqCode);
			logger.debug("notifyReqCode=" + ObjectUtils.toString(notifyReqCode));
			String parseCode = notifyReqCode.getParseCode();
			String ver = notifyReqCode.getVer();
			String mod = notifyReqCode.getModuleName();
			CallBackType callBackType = notifyReqCode.getCallType();
			String serviceName = notifyReqCode.getServiceName();
			String namespace = notifyReqCode.getNamespace();
			responseType=callBackType.toName();
			NotifyReqParser parser = NotifyReqParser.getInstance(namespace, parseCode);
			NotfyReqParm notfyReqParm = null;
			try {
				notfyReqParm = parser.parseRequest(request, notifyReqCode);
				serviceName = notifyReqCode.getServiceName();
			} catch (Exception e1) {
				logger.error("" + e1, e1);
				if (e1 instanceof ValidationException) {
					ValidationException ve = (ValidationException) e1;
					if(callBackType==CallBackType.ASYNC_CALLBACK){
						callbackBean = NotifyStrCallbackBean.ERR("参数【" + ve.getArgName() + "】" + ve.getMessage());
					}else {
						callbackBean = NotifyJspCallbackBean.ERR("参数【" + ve.getArgName() + "】" + ve.getMessage());
					}
					handlerResult(namespace, request, response, callBackType, callbackBean);
					return;
				}

			}
			notfyReqParm.setReqCodeParm(notifyReqCode);
			String platformNo = notfyReqParm.getPlatformNo();
			if (responseType.isEmpty()) {
				throw new Exception("回调类型为空！");
			}
			if (platformNo.isEmpty()) {
				throw new Exception("平台编码为空！");
			}
			String mdcLogid = MDC.get("logid");
			if (StringUtils.isEmpty(mdcLogid)) {
				mdcLogid = "0";
			}
			Long logid = Long.valueOf(mdcLogid);
			if (logid == null) {
				logid = 0l;
			}

			String requestNo = notfyReqParm.getRequestNo();// (String) respMap.get("requestno");
			String platformUserNo = notfyReqParm.getPlatformUserNo();
			AkaFrameProperties.Protocol protocolInfo=uIFrameAppConfig.getProtocolInfo(namespace);
			String reqPackage = protocolInfo.getPackageName();
			String preReqClassPath = reqPackage + "." + ver + "." + mod + "." ;

			// 插入回掉请求日志
			String className = preReqClassPath+serviceName;
			int nodifyId = 0;
			InterLogReqBean interLogReq = null;
			String remoteIp = NetTools.getRemoteAddr(request);
			InterLogNotifyBean insertNotify=null;
			try {
				interLogReq = BeanGet.getBean(InterLogReqDao.class, request).getInterLogReq(requestNo, serviceName);
				insertNotify=this.createInsertNotify(request,interLogReq,
						notfyReqParm,
						reqCode,
						responseType,
						serviceName,
						className);
				//插入
				nodifyId = BeanGet.getBean(InterLogNotifyDao.class, request).insert(insertNotify);
			} catch (Exception e) {
				logger.error("插入异常！" + e,e);
				// 重复插入
				if (!className.isEmpty()) {// 不为空
					callbackBean = handerMore(request,
							namespace,
							mod,
							ver,
							remoteIp,
							className,
							callBackType,
							insertNotify,
							interLogReq,
							notfyReqParm,
							start);
					handlerResult(namespace, request, response, callBackType, callbackBean);
					return;
				}
				throw e;

			}
			String clazzName=preReqClassPath + className;
			callbackBean=hander(request,namespace,mod,ver,remoteIp,clazzName,callBackType,nodifyId,interLogReq,
					notfyReqParm,start);
			handlerResult(namespace, request, response, callBackType, callbackBean);
			return;

		} catch (Exception e) {
			logger.error(e, e);

		} finally {
			if (callbackBean != null) {
				logger.info("responseType=" + responseType);
				logger.info("return callbackBean=" + ObjectUtils.toJsonString(callbackBean));
			}
			logger.info("callback====++++=====end==========");
		}

	}

	public InterLogNotifyBean createInsertNotify(HttpServletRequest request,
												 InterLogReqBean interLogReq,
												 NotfyReqParm notfyReqParm,
												 String reqCode,
												 String responseType,
												 String serviceName,
												 String className)throws Exception{
		InterLogNotifyBean insertNotify = new InterLogNotifyBean();
		insertNotify.setRespData(notfyReqParm.getResponseString());
		insertNotify.setDoneStatus(UiFrameConstants.HandleStatus.doing);// 处理中
		insertNotify.setReqCode(reqCode);
		insertNotify.setResponseType(responseType);
		String platformNo = notfyReqParm.getPlatformNo();
		insertNotify.setPlatformNo(platformNo);
		String mdcLogid = MDC.get("logid");
		if (StringUtils.isEmpty(mdcLogid)) {
			mdcLogid = "0";
		}
		Long logid = Long.valueOf(mdcLogid);
		if (logid == null) {
			logid = 0l;
		}
		insertNotify.setLogid(logid);
		String requestNo = notfyReqParm.getRequestNo();// (String) respMap.get("requestno");
		insertNotify.setRequestNo(requestNo);
		String platformUserNo = notfyReqParm.getPlatformUserNo();
		insertNotify.setPlatformUserNo(platformUserNo);
		String status = notfyReqParm.getStatus();
		String errorCode = notfyReqParm.getErrorCode();
		String errorMessage = notfyReqParm.getErrorCode();
		String code = notfyReqParm.getCode();
		insertNotify.setCode(code);
		insertNotify.setStatus(status);
		insertNotify.setErrorCode(errorCode);
		insertNotify.setErrorMessage(errorMessage);
		insertNotify.setServiceName(serviceName);
		insertNotify.setAddTime(LocalDateTime.now());

		if (interLogReq != null) {
			if (className.isEmpty()) {
				className = interLogReq.getClassName();
			}
			String platFormUserId = interLogReq.getPlatformUserNo();
			if (StringUtils.isEmpty(platformUserNo)) {
				platformUserNo = platFormUserId;
			}
			// 插入
		} else {
		}
		if (className.isEmpty()) {
			throw new Exception("clasName为空！");
		}
		notfyReqParm.setPlatformUserNo(platformUserNo);
		insertNotify.setPlatformUserNo(platformUserNo);
		insertNotify.setClassName(className);

		return insertNotify;
	}
	private CallbackBean hander(
			HttpServletRequest request,
			String namespaceKey,
			String mod,
			String ver,
			String remoteIp,
			String className,
			CallBackType callBackType,
			int nodifyId,
			InterLogReqBean interLogReq,
			NotfyReqParm notfyReqParm,
			long start)throws Exception{
		CallbackBean callbackBean = null;
		InterLogNotifyBean updateNotifiy = new InterLogNotifyBean();
		updateNotifiy.setId((long) nodifyId);
		String exceptionStr = "";
		String requestNo = notfyReqParm.getRequestNo();
		// 请求受理或处理成功，根据不同接口处理
		try {

			if (StringUtils.hasText(className)) {
				String clazzName = className;
				logger.info("hander class name=" + clazzName);
				Class<?> clazz = Class.forName(clazzName);
				ICallBack proObj = (ICallBack) clazz.newInstance();
				if (interLogReq != null) {
					String classReqArgs = interLogReq.getClassReqArgs();
					if (StringUtils.hasText(classReqArgs)) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						Map<String, Object> map = (Map) ObjectUtils.fromJsonToMap(classReqArgs);
						RequestUtils ru = new RequestUtils();
						for (String key : map.keySet()) {
							ru.setValue(key, map.get(key));
						}
						Exchange.injectRequestParmater((Protocol) proObj, ru);

					}
				} else {
					Protocol pc = (Protocol) proObj;
					pc.namespace = namespaceKey;
					pc.moduleName = mod;
					pc.ver = ver;
					pc.protocol = clazzName;
					pc.remoteIp = remoteIp;
					pc.userid = notfyReqParm.getPlatformUserNo();
				}
				RequestUtils ru=new RequestUtils();
				ru.setObject(UiFrameConstants.PROTOCOL_REQ_OBJ,proObj);
				ru.setObject(UiFrameConstants.PROTOCOL_REQ_REQUEST,request);
				UiFrameConstants.PROTOCOL_REQ_PARM_BN,
				ReqContext.setRequestBean((Protocol) proObj);
				callbackBean = proObj.callBack(callBackType, notfyReqParm, interLogReq);
				logger.debug("call return=" + ObjectUtils.toJsonString(callbackBean));
				if (callbackBean.getStatus() == 1) {// 成功
					updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.suc);
				} else {// 处理失败
					updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.fail);
					exceptionStr = callbackBean.getMessage();
					updateNotifiy.setErrorMessage(exceptionStr);

				}
			}

		} catch (Exception e) {
			logger.error(e + "", e);
			if (e instanceof InvocationTargetException) {
				e = (Exception) ((InvocationTargetException) e).getTargetException();
			}
			updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.fail);
			updateNotifiy.setErrorCode(IError.COMMON_ERROR + "");
			exceptionStr = StringUtils.trim(e.getMessage());
			updateNotifiy.setErrorMessage(exceptionStr);
			callbackBean = getErroBean(callBackType,IError.BG_ERROR, "流水号：" + requestNo + "," + exceptionStr);
		}


		if (callbackBean != null) {
			String str=ObjectUtils.toJsonString(callbackBean);
			updateNotifiy.setReturnStr(str);
		}
		long end = System.currentTimeMillis();
		updateNotifiy.setHandlerTimes((int) (end - start));
		InterLogNotifyDao.getInstance(namespaceKey).update(updateNotifiy);

		return callbackBean;
	}
	private CallbackBean getErroBean(CallBackType callBackType,int errorCode, String errorMsg) {

		CallbackBean callbackBean = CallbackBeanFactory.getCallbackBean(callBackType);
		callbackBean.setStatus(0);
		callbackBean.setError(errorCode);
		callbackBean.setMessage(IError.get(errorCode, errorMsg));
		return callbackBean;
	}

	private CallbackBean handerMore(
			HttpServletRequest request,
			String namespaceKey,
			String mod,
			String ver,
			String remoteIp,
			String preReqClassPath,
			CallBackType callBackType,
			InterLogNotifyBean insertNotify,
			InterLogReqBean interLogReq,
			NotfyReqParm notfyReqParm,
			long start) throws Exception {

		CallbackBean callbackBean = null;

		// 有唯一索引可防止重复提交
		int interNotifyMoreId = 0;
		String requestNo = (String) notfyReqParm.getRequestNo();
		try {
			interNotifyMoreId = InterLogNotifyMoreDao.getInstance(namespaceKey).insert(insertNotify);
		} catch (Exception e1) {
			return getErroBean(callBackType,IError.BG_ERROR, " 流水号：" + requestNo + "," + e1);
		}
		try {

			String clazzName = preReqClassPath;
			logger.info("1--hander class name=" + clazzName);
			Class<?> clazz = Class.forName(clazzName);
			ICallBack proObj = (ICallBack) clazz.newInstance();

			if (interLogReq != null) {
				String classReqArgs = interLogReq.getClassReqArgs();
				if (StringUtils.hasText(classReqArgs)) {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Map<String, Object> map = (Map) ObjectUtils.fromJsonToMap(classReqArgs);
					RequestUtils ru = new RequestUtils();
					for (String key : map.keySet()) {
						ru.setValue(key, map.get(key));
					}
					Exchange.injectRequestParmater((Protocol) proObj, ru);

				}
			} else {
				Protocol pc = (Protocol) proObj;
				pc.infkey = namespaceKey;
				pc.moduleName = mod;
				pc.ver = ver;
				pc.protocol = clazzName;
				pc.remoteIp = remoteIp;
				pc.userid = notfyReqParm.getPlatformUserNo();
			}
			ReqContext.setRequestBean((Protocol) proObj);
			callbackBean = proObj.callBackMore(callBackType, notfyReqParm, interLogReq);
		} catch (Exception e) {
			logger.error(e + "", e);
			if (e instanceof InvocationTargetException) {
				e = (Exception) ((InvocationTargetException) e).getTargetException();
			}
			callbackBean = getErroBean(callBackType,IError.BG_ERROR, " 流水号：" + requestNo + "," + e);
		}

		InterLogNotifyMoreBean updateNotifiyMore = new InterLogNotifyMoreBean();
		String errorCode = StringUtils.trim((String) notfyReqParm.getErrorCode());
		String errorMessage = StringUtils.trim((String) notfyReqParm.getErrorMessage());
		updateNotifiyMore.setErrorCode(errorCode + "");
		updateNotifiyMore.setErrorMessage(errorMessage);
		updateNotifiyMore.setId((long) interNotifyMoreId);
		if (callbackBean != null) {
			String str=ObjectUtils.toJsonString(callbackBean);
			updateNotifiyMore.setReturnStr(str);
			if (callbackBean.getStatus() == 1) {
				updateNotifiyMore.setDoneStatus(UiFrameConstants.HandleStatus.suc);
			} else {
				updateNotifiyMore.setDoneStatus(UiFrameConstants.HandleStatus.fail);
			}
		}
		long end = System.currentTimeMillis();
		updateNotifiyMore.setHandlerTimes((int) (end - start));
		InterLogNotifyMoreDao.getInstance(namespaceKey).update(updateNotifiyMore);

		return callbackBean;

	}

	private void handlerResult(String namespaceKey, HttpServletRequest request, HttpServletResponse response, CallBackType callBackType,
							   CallbackBean callbackBean) throws Exception {
		if (callBackType==CallBackType.ASYNC_CALLBACK) {
			response.setContentType("text/html;charset=utf-8");
			NotifyStrCallbackBean notifyStrCallbackBean=(NotifyStrCallbackBean)callbackBean;
			if(callbackBean.getStatus()==1) {
				response.getWriter().write(notifyStrCallbackBean.getData().sucTxt);
			}else{
				response.getWriter().write(notifyStrCallbackBean.getData().failTxt);
			}

		} else if (callBackType==CallBackType.GATEWAY_CALLBACK
				|| callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK) {
			dispath(namespaceKey, request, response, callbackBean);

		}
	}

	private void dispath(String namespaceKey, HttpServletRequest request, HttpServletResponse response,
			CallbackBean callbackBean) throws Exception {
		String forwardURL = UiFrameConstants.NOTIFY_CALLBAK_JSP;
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
		request.setAttribute(UiFrameConstants.NOTIFY_CALLBACK_BEAN, callbackBean);
		request.setAttribute(UiFrameConstants.NOTIFY_CALLBACK_BEAN_INTER_KEY, namespaceKey);
		String r = StringUtils.trim(request.getParameter("r"));
		if (StringUtils.hasText(r)) {
			request.setAttribute(UiFrameConstants.NOTIFY_REDIRECT_URL, r);
		}
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	public static void main(String[] args) {

	}
}
