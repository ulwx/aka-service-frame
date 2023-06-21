package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.CallBackType;
import com.github.ulwx.aka.frame.protocol.InterType;
import org.apache.log4j.Logger;

import java.util.Map;


public  class Protocol {

	protected  Logger logger = Logger.getLogger(this.getClass());
	//订单号
	private String requestid;
	//服务名称，由业务来确定
	private String serviceName;
	//平台id,由业务来定
	private String platformNo;
	//平台用户id，由业务来定
	private String platformUserNo;
	//本地用户id
	private String userid;
	private String udid;
	private String sourceId;
	private String protocolName;// 10001
	private String ver; //协议版本号 1.0.0
	private Long timestamp;
	//关联单号
	private String refRequestid;
	private String remoteIp;
	private String moduleName;//moduleName
	private String namespace;
	private InterType interType;
	private CallBackType callBackType;

	//请求时的扩展
	private  Map<String,Object> extMap;
	private Map<String, String[]> requestMap;
	private String requestBody;

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}


	public Map<String, String[]> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String[]> requestMap) {
		this.requestMap = requestMap;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public CallBackType getCallBackType() {
		return callBackType;
	}

	public void setCallBackType(CallBackType callBackType) {
		this.callBackType = callBackType;
	}

	public InterType getInterType() {
		return interType;
	}

	public void setInterType(InterType interType) {
		this.interType = interType;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getRefRequestid() {
		return refRequestid;
	}

	public void setRefRequestid(String refRequestid) {
		this.refRequestid = refRequestid;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Map<String, Object> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}
}
