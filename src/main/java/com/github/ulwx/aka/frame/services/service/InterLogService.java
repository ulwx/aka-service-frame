package com.github.ulwx.aka.frame.services.service;

import com.alibaba.fastjson.JSON;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.protocol.HandleStatus;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.services.domain.InterLogReqBean;
import com.github.ulwx.aka.webmvc.AkaServiceSupport;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.IpUtils;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.StringUtils;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;

public class InterLogService extends AkaServiceSupport {

	public InterLogService() {
		// TODO Auto-generated constructor stub
	}


	public long insertLog(Protocol pro, Class<? extends ActionSupport> actionClass) throws Exception {
		Map<String,Object> extMap=pro.getExtMap();
		String userDevice = pro.getSourceId();
		Class<?> clazz = actionClass;
		InterLogReqBean interLog = new InterLogReqBean();
		String requestNo = pro.getRequestid();
		interLog.setRequestNo(requestNo);
		if(pro.getRequestBody()!=null) {
			String reqData = pro.getRequestBody();
//			if (reqData.length() > 2000) {
//				reqData = reqData.substring(0, 2000);
//			}
			interLog.setReqData(reqData);
		}
		String reqArgs = ObjectUtils.toStringUseFastJson(pro.getRequestMap(), false);
//		if (reqArgs.length() > 1000) {
//			reqArgs = reqArgs.substring(0, 1000);
//		}
		interLog.setClassReqArgs(reqArgs);
		interLog.setDoneStatus(HandleStatus.doing.value());
		String mdcLogid = MDC.get("logid");
		if (StringUtils.isEmpty(mdcLogid)) {
			mdcLogid = "0";
		}
		Long logid = Long.valueOf(mdcLogid);
		if (logid == null) {
			logid = 0l;
		}
		if(extMap!=null) {
			interLog.setExtStr(ObjectUtils.toStringUseFastJson(extMap));
		}
		interLog.setLogid(logid);
		interLog.setPlatformNo(pro.getPlatformNo());
		interLog.setIp(IpUtils.getLocalIp());
		String userId = pro.getUserid();
		interLog.setPlatformUserNo(userId);
		interLog.setReturnStr("");
		interLog.setClassName(clazz.getSimpleName());
		interLog.setServiceName(pro.getServiceName());

		if (StringUtils.hasText(pro.getRefRequestid())) {
			interLog.setRefReqNo(pro.getRefRequestid());
		}

		interLog.setType(pro.getInterType()+"");
		interLog.setResponseType(pro.getCallBackType()+"");
		interLog.setUserDevice(userDevice);
		long interLogId = beanGet.bean(InterLogReqDao.class).insert(interLog);
		return interLogId;

	}

	public void updateLog(long interLogId, int status,
						  String errorCode,
						  String code,
						  String errorMessage,
						  HandleStatus handStatus,
						  String ret,
						  long start) throws Exception {
//		if (ret.length() > 500) {
//			ret = ret.substring(0,  500);
//		}
		InterLogReqBean updateInterLog = new InterLogReqBean();
		updateInterLog.setId(interLogId);
		updateInterLog.setStatus(status+"");
		updateInterLog.setErrorCode(errorCode);
		updateInterLog.setCode(code);
//		if (errorMessage.length() > 500) {
//			errorMessage = errorMessage.substring(0,  500);
//		}
		updateInterLog.setErrorMessage(errorMessage);
		updateInterLog.setDoneStatus(handStatus.value());
		updateInterLog.setReturnStr(ret);
		updateInterLog.setHandlerTimes((int)(System.currentTimeMillis()-start));
		beanGet.bean(InterLogReqDao.class).update(updateInterLog);
	}

}
