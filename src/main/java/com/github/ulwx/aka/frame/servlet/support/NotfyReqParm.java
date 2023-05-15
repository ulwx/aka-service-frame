package com.github.ulwx.aka.frame.servlet.support;

import java.util.HashMap;
import java.util.Map;

public class NotfyReqParm {
	//请求流水号
	private String requestNo="";
	//平台id
	private String platformNo="";
	//响应里的状态码，一般是整个相应的成功或失败状态码
	private String status="" ;
	//响应里的错误码
	private String errorCode="" ;
	//错误消息
	private String errorMessage="" ;
	//响应里的code，具体根据不同业务响应的内容而不同
	private String code="";
	//响应
	private String responseString="";
	//平台用户id
	private String platformUserNo="";
	//来源id，例如pc，minisite等
	private String sourceId="";
	//请求信息
	private ReqCodeParm  reqCodeParm;
	//请求的参数Map，Map里存放所有请求的参数
	private Map<String, Object>  requestMap=new HashMap<>();

	public ReqCodeParm getReqCodeParm() {
		return reqCodeParm;
	}

	/**
	 * 请求信息
	 * @param reqCodeParm
	 */
	public void setReqCodeParm(ReqCodeParm reqCodeParm) {
		this.reqCodeParm = reqCodeParm;
	}



	public String getSourceId() {
		return sourceId;
	}

	/**
	 * 来源id，例如pc，minisite等
	 * @param sourceId
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Map<String, Object>  getRequestMap() {
		return requestMap;
	}

	/**
	 * 请求的参数Map，Map里存放所有请求的参数
	 * @param requestMap
	 */
	public void setRequestMap(Map<String, Object>  requestMap) {
		this.requestMap = requestMap;
	}



	public String getPlatformUserNo() {
		return platformUserNo;
	}

	/**
	 * 平台用户id
	 * @param platformUserNo
	 */
	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}



	public String getResponseString() {
		return responseString;
	}

	/**
	 * 响应字符串
	 * @param responseString
	 */
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 响应里的code，具体根据不同业务响应的内容而不同
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}



	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * 平台id
	 * @param platformNo
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * 响应里的状态码，一般是整个相应的成功或失败状态码
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 响应里的错误码
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 响应里的错误消息
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getRequestNo() {
		return requestNo;
	}

	/**
	 * 请求流水号
	 * @param requestNo
	 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}


	public NotfyReqParm() {
		// TODO Auto-generated constructor stub
	}

}
