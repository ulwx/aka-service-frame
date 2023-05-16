package com.github.ulwx.aka.frame.services.service;

import com.alibaba.fastjson.JSON;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.InterParam;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.protocol.req.IProtocol;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.DownLoadBean;
import com.github.ulwx.aka.frame.protocol.res.JSPBean;
import com.github.ulwx.aka.frame.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.webmvc.AkaServiceSupport;
import com.ulwx.tool.IpUtils;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;

public class InterLogService extends AkaServiceSupport {

	public InterLogService() {
		// TODO Auto-generated constructor stub
	}

	public long insertLog(Protocol pro) throws Exception {
		InterParam ip = new InterParam();
		ip.setInterType(((IProtocol)pro).getInterType());
		ip.setPlatformNo("");
		ip.setExtMap(null);
		ip.setProtocol(pro);
		ip.setRefReqNo("");
		ip.setReqCodePart(new InterParam.ReqCodePart(pro.getClass().getSimpleName()));
		ip.setRequestNo(pro.requestid);
		ip.setUserId(pro.userid);
		return insertLog(ip, null);
	}

	public long insertLog(InterParam interParam, Map<String, Object> argmap) throws Exception {

		String userId = interParam.getUserId()==null?"":interParam.getUserId()+"";
		String refReqNo = interParam.getRefReqNo();
		String userDevice = "";
		String namespace = "";
		Class<?> clazz = null;

		if (interParam.getProtocol() != null) {
			clazz = interParam.getProtocol().getClass();
			namespace = interParam.getProtocol().namespace;
			userDevice = interParam.getProtocol().sourceId;
		}

		InterLogReqBean interLog = new InterLogReqBean();
		String requestNo = interParam.getRequestNo();
		interLog.setRequestNo(requestNo);
		if (argmap != null) {
			String reqData = JSON.toJSONString(argmap);
			if (reqData.length() > 2000) {
				reqData = reqData.substring(0, 2000);
			}
			interLog.setReqData(reqData);
		}
		RequestUtils ru = interParam.getProtocol().getRequest();
		interParam.getProtocol().setRequest(null);
		String reqArgs = ObjectUtils.toStringUseFastJson(interParam.getProtocol(), false);
		if (reqArgs.length() > 1000) {
			reqArgs = reqArgs.substring(0, 1000);
		}
		interLog.setClassReqArgs(reqArgs);
		interParam.getProtocol().setRequest(ru);
		interLog.setDoneStatus(UiFrameConstants.HandleStatus.doing);
		interLog.setKeySerial("");
		String mdcLogid = MDC.get("logid");
		if (StringUtils.isEmpty(mdcLogid)) {
			mdcLogid = "0";
		}
		Long logid = Long.valueOf(mdcLogid);
		if (logid == null) {
			logid = 0l;
		}
		if (interParam.getReqCodePart() != null) {
			String reqCodeStr = interParam.getReqCode().toReqCodeString();
			interLog.setReqCode(reqCodeStr);
		}
		if (interParam.getExtMap() != null) {
			interLog.setExtStr(ObjectUtils.toStringUseFastJson((interParam.getExtMap())));
		}
		interLog.setLogid(logid);
		interLog.setPlatformNo(interParam.getPlatformNo());
		interLog.setIp(IpUtils.getLocalIp());
		interLog.setPlatformUserNo(userId);
		interLog.setReturnStr("");
		interLog.setClassName(clazz.getName());
		if (interParam.getReqCodePart() != null) {
			interLog.setServiceName(interParam.getReqCodePart().getServiceName());
		}

		if (StringUtils.hasText(refReqNo)) {
			interLog.setRefReqNo(refReqNo);
		} else {
			if(StringUtils.hasText(interParam.getProtocol().requestid)) {
				interLog.setRefReqNo(interParam.getProtocol().requestid);
			}else {
			}
		}

		interLog.setType(interParam.getInterType());
		interLog.setUserDevice(userDevice);
		
		Protocol pro=interParam.getProtocol();
		UIFrameAppConfig uiFrameAppConfig=beanGet.bean(UIFrameAppConfig.class);
		AkaFrameProperties.LogConfig
				logConfig= uiFrameAppConfig.getStorage().getLogConfig();
		boolean insert=true;
		List<String> insertLogNotInsert = logConfig.getExcludeProtocol();
		String compStr=pro.ver+"."+pro.moduleName+"."+pro.getClass().getSimpleName();
		if(insertLogNotInsert.isEmpty()) {
			for(int i=0; i<insertLogNotInsert.size(); i++) {
				if(insertLogNotInsert.get(i).trim().equals(compStr)) {
					insert=false;////
					break;
				}
			}
		}
		if(insert) {
			long interLogId = beanGet.bean(InterLogReqDao.class).insert(interLog);
			return interLogId;
		}
		return 0;

	}

	public void updateLog( long interLogId, BaseRes javaBean,long start) throws Exception {
		int doneStatus = UiFrameConstants.HandleStatus.fail;
		if (javaBean.getStatus() == 1) {
			doneStatus = UiFrameConstants.HandleStatus.suc;
		}
		if (javaBean instanceof JSPBean || javaBean instanceof DownLoadBean) {

			updateLog(interLogId, javaBean.getStatus() + "", doneStatus,
					javaBean.getMessage(),start);
		} else {
			String retStr = ObjectUtils.toStringUseFastJson(javaBean);
			updateLog(interLogId, javaBean.getStatus() + "", doneStatus, retStr,start);
		}

	}

	public void updateLog(long interLogId, String status,
						  int doneStatus,
						  String ret,long start) throws Exception {
		if (ret.length() > 500) {
			ret = ret.substring(0, 500);
		}
		InterLogReqBean updateInterLog = new InterLogReqBean();
		updateInterLog.setId(interLogId);
		updateInterLog.setStatus(status);
		updateInterLog.setDoneStatus(doneStatus);
		updateInterLog.setReturnStr(ret);
		updateInterLog.setHandlerTimes((int)(System.currentTimeMillis()-start));
		beanGet.bean(InterLogReqDao.class).update(updateInterLog);
	}

}
