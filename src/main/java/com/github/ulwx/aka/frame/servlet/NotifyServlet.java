//package com.github.ulwx.aka.frame.servlet;
//
//import com.github.ulwx.aka.frame.AkaFrameProperties;
//import com.github.ulwx.aka.frame.protocol.Exchange;
//import com.github.ulwx.aka.frame.protocol.req.CallBackProtocal;
//import com.github.ulwx.aka.frame.protocol.CallBackType;
//import com.github.ulwx.aka.frame.protocol.req.Protocol;
//import com.github.ulwx.aka.frame.protocol.res.CallbackBean;
//import com.github.ulwx.aka.frame.protocol.services.dao.InterLogNotifyDao;
//import com.github.ulwx.aka.frame.protocol.services.dao.InterLogNotifyMoreDao;
//import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyBean;
//import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyMoreBean;
//import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
//import com.github.ulwx.aka.frame.protocol.utils.IError;
//import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
//import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
//import com.github.ulwx.aka.frame.servlet.support.*;
//import com.github.ulwx.aka.frame.utils.NetTools;
//import com.github.ulwx.aka.frame.UIFrameAppConfig;
//import com.github.ulwx.aka.webmvc.BeanGet;
//import com.ulwx.tool.NetUtils;
//import com.ulwx.tool.ObjectUtils;
//import com.ulwx.tool.RequestUtils;
//import com.ulwx.tool.StringUtils;
//import org.apache.log4j.Logger;
//import org.slf4j.MDC;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.time.LocalDateTime;
//import java.util.Map;
//
///**
// * 接口servlet
// */
//@MultipartConfig
//@WebServlet(name = "NotifyServlet", urlPatterns = "/notify/intf.do")
//public class NotifyServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private Logger logger = Logger.getLogger(NotifyServlet.class);
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//
//        //InterLogNotifyBean insertNotify = new InterLogNotifyBean();
//        CallbackBean callbackBean = null;
//        long start = System.currentTimeMillis();
//        AkaFrameProperties akaFrameProperties = BeanGet.getBean(AkaFrameProperties.class, request);
//        UIFrameAppConfig uIFrameAppConfig = BeanGet.getBean(UIFrameAppConfig.class, request);
//        try {
//            request.setCharacterEncoding("UTF-8");
//            logger.info("callback request==========+++++++++++======");
//
//            String queryStr = NetUtils.urlMapToQueryStr(request.getParameterMap(), "utf-8");
//            logger.info("queryStr=" + queryStr);
//            logger.info("request=" + ObjectUtils.toJsonString(request.getParameterMap()));
//            logger.info("request URI=" + request.getRequestURI());
//            String reqCodeArgName = StringUtils.trim(akaFrameProperties.getNotifyRequest().getReqcodeArgname());
//            if (reqCodeArgName.isEmpty()) {
//                reqCodeArgName = UiFrameConstants.NOTIFY_REQ_CODE_ARGNAME;
//            }
//            // reqCode区分接口逻辑，[ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace]
//            // v1@trade@jjs@1@CUSTOMER_REGISTER@@intf
//            // callType：1-异步回调 2-网关回调 3-本地窗体
//            String reqCode = StringUtils.trim(request.getParameter(reqCodeArgName));
//
//            NotifyProtocolInfo notifyParms = new NotifyProtocolInfo(reqCode, request);
//            AkaFrameProperties.Protocol protocolInfo = uIFrameAppConfig.getProtocolInfo(notifyParms.getNamespace());
//            String reqPackage = protocolInfo.getPackageName();
//            // 插入回掉请求日志
//            String className = reqPackage + "." + notifyParms.getVer() + "." + notifyParms.getMod() + "." + notifyParms.getServiceName();
//            notifyParms.setPrototolClassName(className);
//            NotifyReqParser parser = NotifyReqParser.getInstance(notifyParms.getNamespace(),
//                    notifyParms.getParseCode());
//            RequestInfo parserResult = null;
//            try {
//                parserResult = parser.parseRequest(notifyParms);
//            } catch (Exception e1) {
//                logger.error("" + e1, e1);
//                if (e1 instanceof ValidationException) {
//                    ValidationException ve = (ValidationException) e1;
//                    if (notifyParms.getCallBackType() == CallBackType.ASYNC_CALLBACK) {
//                        callbackBean = NotifyStrCallbackBean.ERR("参数【" + ve.getArgName() + "】" + ve.getMessage());
//                    } else {
//                        callbackBean = NotifyJspCallbackBean.ERR("参数【" + ve.getArgName() + "】" + ve.getMessage());
//                    }
//                    handlerResult(notifyParms, response, callbackBean);
//                    return;
//                }
//
//            }
//            parserResult.setNotifyParms(notifyParms);
//            String platformNo = parserResult.getPlatformNo();
//            if (notifyParms.getCallBackType().toName().isEmpty()) {
//                throw new Exception("回调类型为空！");
//            }
//            if (platformNo.isEmpty()) {
//                throw new Exception("平台编码为空！");
//            }
//            String mdcLogid = MDC.get("logid");
//            if (StringUtils.isEmpty(mdcLogid)) {
//                mdcLogid = "0";
//            }
//            Long logid = Long.valueOf(mdcLogid);
//            if (logid == null) {
//                logid = 0l;
//            }
//
//            String requestNo = parserResult.getRequestNo();// (String) respMap.get("requestno");
//            String platformUserNo = parserResult.getPlatformUserNo();
//
//            int nodifyId = 0;
//           // InterLogReqBean interLogReq = null;
//            String remoteIp = NetTools.getRemoteAddr(request);
//            InterLogNotifyBean insertNotify = null;
//            try {
//                insertNotify = this.createInsertNotify(notifyParms, parserResult, className);
//                //插入
//                nodifyId = BeanGet.getBean(InterLogNotifyDao.class, request).insert(insertNotify);
//            } catch (Exception e) {
//                logger.error("插入异常！" + e, e);
//                // 重复插入
//                if (!className.isEmpty()) {// 不为空
//                    callbackBean = handerCallBackMore(request,notifyParms, parserResult, remoteIp, className,
//                            insertNotify,  start);
//                    handlerResult(notifyParms, response, callbackBean);
//                    return;
//                }
//                throw e;
//
//            }
//            callbackBean = handerCallBack(request, namespace, mod, ver, remoteIp, className, callBackType, nodifyId, interLogReq, notfyReqParm, start);
//            handlerResult(namespace, request, response, callBackType, callbackBean);
//            return;
//
//        } catch (Exception e) {
//            logger.error(e, e);
//
//        } finally {
//            if (callbackBean != null) {
//                logger.info("responseType=" + responseType);
//                logger.info("return callbackBean=" + ObjectUtils.toJsonString(callbackBean));
//            }
//            logger.info("callback====++++=====end==========");
//        }
//
//    }
//
//    public InterLogNotifyBean createInsertNotify(NotifyProtocolInfo notifyParms, RequestInfo notfyParserResult, String className) throws Exception {
//        InterLogNotifyBean insertNotify = new InterLogNotifyBean();
//        insertNotify.setRespData(notfyParserResult.getResponseString());
//        insertNotify.setDoneStatus(UiFrameConstants.HandleStatus.doing);// 处理中
//        insertNotify.setReqCode(notifyParms.getReqCode());
//        insertNotify.setResponseType(notifyParms.getCallBackType().toName());
//        String platformNo = notfyParserResult.getPlatformNo();
//        insertNotify.setPlatformNo(platformNo);
//        String mdcLogid = MDC.get("logid");
//        if (StringUtils.isEmpty(mdcLogid)) {
//            mdcLogid = "0";
//        }
//        Long logid = Long.valueOf(mdcLogid);
//        if (logid == null) {
//            logid = 0l;
//        }
//        insertNotify.setLogid(logid);
//        String requestNo = notfyParserResult.getRequestNo();// (String) respMap.get("requestno");
//        insertNotify.setRequestNo(requestNo);
//        String platformUserNo = notfyParserResult.getPlatformUserNo();
//        insertNotify.setPlatformUserNo(platformUserNo);
//        String status = notfyParserResult.getStatus();
//        String errorCode = notfyParserResult.getErrorCode();
//        String errorMessage = notfyParserResult.getErrorCode();
//        String code = notfyParserResult.getCode();
//        insertNotify.setCode(code);
//        insertNotify.setStatus(status);
//        insertNotify.setErrorCode(errorCode);
//        insertNotify.setErrorMessage(errorMessage);
//        insertNotify.setServiceName(notifyParms.getServiceName());
//        insertNotify.setAddTime(LocalDateTime.now());
//        insertNotify.setPlatformUserNo(platformUserNo);
//        insertNotify.setPlatformUserNo(platformUserNo);
//        insertNotify.setClassName(className);
//
//        return insertNotify;
//    }
//
//    private CallbackBean handerCallBack(HttpServletRequest request, String namespaceKey,
//                                        String mod, String ver, String remoteIp,
//                                        String className, CallBackType callBackType,
//                                        int nodifyId, InterLogReqBean interLogReq, RequestInfo notfyReqParm, long start) throws Exception {
//        CallbackBean callbackBean = null;
//        InterLogNotifyBean updateNotifiy = new InterLogNotifyBean();
//        updateNotifiy.setId((long) nodifyId);
//        String exceptionStr = "";
//        String requestNo = notfyReqParm.getRequestNo();
//        // 请求受理或处理成功，根据不同接口处理
//        try {
//
//            if (StringUtils.hasText(className)) {
//                String clazzName = className;
//                logger.info("hander class name=" + clazzName);
//                Class<?> clazz = Class.forName(clazzName);
//                CallBackProtocal proObj = (CallBackProtocal) clazz.newInstance();
//
//                RequestUtils ru = new RequestUtils();
//                ru.setObject(UiFrameConstants.PROTOCOL_REQ_OBJ, proObj);
//                ru.setObject(UiFrameConstants.PROTOCOL_REQ_REQUEST, request);
//                ReqContext.setRequestUtis(ru);
//                callbackBean = proObj.callBack(callBackType, notfyReqParm, interLogReq);
//                logger.debug("call return=" + ObjectUtils.toJsonString(callbackBean));
//                if (callbackBean.getStatus() == 1) {// 成功
//                    updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.suc);
//                } else {// 处理失败
//                    updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.fail);
//                    exceptionStr = callbackBean.getMessage();
//                    updateNotifiy.setErrorMessage(exceptionStr);
//
//                }
//            }
//
//        } catch (Exception e) {
//            logger.error(e + "", e);
//            if (e instanceof InvocationTargetException) {
//                e = (Exception) ((InvocationTargetException) e).getTargetException();
//            }
//            updateNotifiy.setDoneStatus(UiFrameConstants.HandleStatus.fail);
//            updateNotifiy.setErrorCode(IError.COMMON_ERROR + "");
//            exceptionStr = StringUtils.trim(e.getMessage());
//            updateNotifiy.setErrorMessage(exceptionStr);
//            callbackBean = getErroBean(callBackType, IError.BG_ERROR, "流水号：" + requestNo + "," + exceptionStr);
//        }
//
//
//        if (callbackBean != null) {
//            String str = ObjectUtils.toJsonString(callbackBean);
//            updateNotifiy.setReturnStr(str);
//        }
//        long end = System.currentTimeMillis();
//        updateNotifiy.setHandlerTimes((int) (end - start));
//        InterLogNotifyDao.getInstance(namespaceKey).update(updateNotifiy);
//
//        return callbackBean;
//    }
//
//    private CallbackBean getErroBean(CallBackType callBackType, int errorCode, String errorMsg) {
//
//        CallbackBean callbackBean = CallbackBeanFactory.getCallbackBean(callBackType);
//        callbackBean.setStatus(0);
//        callbackBean.setError(errorCode);
//        callbackBean.setMessage(IError.get(errorCode, errorMsg));
//        return callbackBean;
//    }
//
//    private CallbackBean handerCallBackMore(
//            HttpServletRequest request,
//            NotifyProtocolInfo notifyParms,
//            RequestInfo parserResult,
//            String remoteIp,
//            String className,
//            InterLogNotifyBean insertNotify,
//            long start) throws Exception {
//
//        CallbackBean callbackBean = null;
//
//        // 有唯一索引可防止重复提交
//        int interNotifyMoreId = 0;
//        String requestNo = (String) parserResult.getRequestNo();
//        try {
//            interNotifyMoreId = BeanGet.getBean(InterLogNotifyMoreDao.class, request).insert(insertNotify);
//        } catch (Exception e1) {
//            return getErroBean(notifyParms.getCallBackType(), IError.BG_ERROR, " 流水号：" + requestNo + "," + e1);
//        }
//        try {
//            logger.info("1--hander class name=" + className);
//            Class<?> clazz = Class.forName(className);
//            CallBackProtocal proObj = (CallBackProtocal) clazz.newInstance();
//            proObj.setCallBackType(notifyParms.getCallBackType());
//            proObj.setInterLogReq();
//            callbackBean = proObj.callBackMore();
//        } catch (Exception e) {
//            logger.error(e + "", e);
//            if (e instanceof InvocationTargetException) {
//                e = (Exception) ((InvocationTargetException) e).getTargetException();
//            }
//            callbackBean = getErroBean(callBackType, IError.BG_ERROR, " 流水号：" + requestNo + "," + e);
//        }
//
//        InterLogNotifyMoreBean updateNotifiyMore = new InterLogNotifyMoreBean();
//        String errorCode = StringUtils.trim((String) notfyReqParm.getErrorCode());
//        String errorMessage = StringUtils.trim((String) notfyReqParm.getErrorMessage());
//        updateNotifiyMore.setErrorCode(errorCode + "");
//        updateNotifiyMore.setErrorMessage(errorMessage);
//        updateNotifiyMore.setId((long) interNotifyMoreId);
//        if (callbackBean != null) {
//            String str = ObjectUtils.toJsonString(callbackBean);
//            updateNotifiyMore.setReturnStr(str);
//            if (callbackBean.getStatus() == 1) {
//                updateNotifiyMore.setDoneStatus(UiFrameConstants.HandleStatus.suc);
//            } else {
//                updateNotifiyMore.setDoneStatus(UiFrameConstants.HandleStatus.fail);
//            }
//        }
//        long end = System.currentTimeMillis();
//        updateNotifiyMore.setHandlerTimes((int) (end - start));
//        InterLogNotifyMoreDao.getInstance(namespaceKey).update(updateNotifiyMore);
//
//        return callbackBean;
//
//    }
//
//    private void handlerResult(NotifyProtocolInfo notifyParms, HttpServletResponse response, CallbackBean callbackBean) throws Exception {
//        if (notifyParms.getCallBackType() == CallBackType.ASYNC_CALLBACK) {
//            response.setContentType("text/html;charset=utf-8");
//            NotifyStrCallbackBean notifyStrCallbackBean = (NotifyStrCallbackBean) callbackBean;
//            if (callbackBean.getStatus() == 1) {
//                response.getWriter().write(notifyStrCallbackBean.getData().sucTxt);
//            } else {
//                response.getWriter().write(notifyStrCallbackBean.getData().failTxt);
//            }
//
//        } else if (notifyParms.getCallBackType() == CallBackType.GATEWAY_CALLBACK) {
//            dispath(notifyParms.getNamespace(), notifyParms.getRequest(), response, callbackBean);
//
//        }
//    }
//
//    private void dispath(String namespaceKey, HttpServletRequest request, HttpServletResponse response, CallbackBean callbackBean) throws Exception {
//        String forwardURL = UiFrameConstants.NOTIFY_CALLBAK_JSP;
//        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
//        request.setAttribute(UiFrameConstants.NOTIFY_CALLBACK_BEAN, callbackBean);
//        request.setAttribute(UiFrameConstants.NOTIFY_CALLBACK_BEAN_INTER_KEY, namespaceKey);
//        String r = StringUtils.trim(request.getParameter("r"));
//        if (StringUtils.hasText(r)) {
//            request.setAttribute(UiFrameConstants.NOTIFY_REDIRECT_URL, r);
//        }
//        dispatcher.forward(request, response);
//    }
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.doGet(req, resp);
//    }
//
//    public static void main(String[] args) {
//
//    }
//}
