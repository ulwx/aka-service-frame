package com.github.ulwx.aka.frame.servlet.support.services.service;

import com.alibaba.fastjson.JSON;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.req.*;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.DownLoadBean;
import com.github.ulwx.aka.frame.protocol.res.JSPBean;
import com.github.ulwx.aka.frame.protocol.res.JSPFormBean;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.support.InterParam;
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
		return beanGet.bean(InterLogService.class).insertLog(ip, null);
	}

	public long insertLog(InterParam tradeParm, Map<String, Object> argmap) throws Exception {

		String userId = tradeParm.getUserId()==null?"":tradeParm.getUserId()+"";
		String refReqNo = tradeParm.getRefReqNo();
		String userDevice = "";
		String namespace = "";
		Class<?> clazz = null;

		if (tradeParm.getProtocol() != null) {
			clazz = tradeParm.getProtocol().getClass();
			namespace = tradeParm.getProtocol().namespace;
			userDevice = tradeParm.getProtocol().sourceId;
		}

		InterLogReqBean interLog = new InterLogReqBean();
		String requestNo = tradeParm.getRequestNo();
		interLog.setRequestNo(requestNo);
		if (argmap != null) {
			String reqData = JSON.toJSONString(argmap);
			if (reqData.length() > 2000) {
				reqData = reqData.substring(0, 2000);
			}
			interLog.setReqData(reqData);
		}
		RequestUtils ru = tradeParm.getProtocol().getRequest();
		tradeParm.getProtocol().setRequest(null);
		String reqArgs = ObjectUtils.toStringUseFastJson(tradeParm.getProtocol(), false);
		if (reqArgs.length() > 1000) {
			reqArgs = reqArgs.substring(0, 1000);
		}
		interLog.setClassReqArgs(reqArgs);
		tradeParm.getProtocol().setRequest(ru);
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
		if (tradeParm.getReqCodePart() != null) {
			String reqCodeStr = tradeParm.getReqCode().toReqCodeString();
			interLog.setReqCode(reqCodeStr);
		}
		if (tradeParm.getExtMap() != null) {
			interLog.setExtStr(ObjectUtils.toStringUseFastJson((tradeParm.getExtMap())));
		}
		interLog.setLogid(logid);
		interLog.setPlatformNo(tradeParm.getPlatformNo());
		interLog.setIp(IpUtils.getLocalIp());
		interLog.setPlatformUserNo(userId);
		interLog.setReturnStr("");
		interLog.setClassName(clazz.getName());
		if (tradeParm.getReqCodePart() != null) {
			interLog.setServiceName(tradeParm.getReqCodePart().getServiceName());
		}

		if (StringUtils.hasText(refReqNo)) {
			interLog.setRefReqNo(refReqNo);
		} else {
			if(StringUtils.hasText(tradeParm.getProtocol().requestid)) {
				interLog.setRefReqNo(tradeParm.getProtocol().requestid);
			}else {
			}
		}

		interLog.setType(tradeParm.getInterType());
		interLog.setUserDevice(userDevice);
		
		Protocol pro=tradeParm.getProtocol();
		AkaFrameProperties akaFrameProperties=beanGet.bean(AkaFrameProperties.class);
		AkaFrameProperties.InsertInterlog insertInterlogInfo= akaFrameProperties.getStorage().getInsertInterlog();
		boolean insert=true;
		List<String> insertLogNotInsert = insertInterlogInfo.getExcludeProtocol();
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

	public void updateLog(String infKey, long interLogId, BaseRes javaBean,long start) throws Exception {
		int doneStatus = UiFrameConstants.HandleStatus.fail;
		if (javaBean.getStatus() == 1) {
			doneStatus = UiFrameConstants.HandleStatus.suc;
		}
		if (javaBean instanceof JSPFormBean || javaBean instanceof JSPBean || javaBean instanceof DownLoadBean) {

			beanGet.bean(InterLogService.class).updateLog(infKey, interLogId, javaBean.getStatus() + "", doneStatus,
					javaBean.getMessage(),start);
		} else {
			String retStr = ObjectUtils.toStringUseFastJson(javaBean);
			beanGet.bean(InterLogService.class).updateLog(infKey, interLogId, javaBean.getStatus() + "", doneStatus, retStr,start);
		}

	}

	public void updateLog(String infKey, long interLogId, String status, int doneStatus, String ret,long start) throws Exception {
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
