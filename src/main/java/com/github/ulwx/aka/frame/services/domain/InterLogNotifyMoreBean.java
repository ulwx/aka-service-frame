package com.github.ulwx.aka.frame.services.domain;

import com.ulwx.tool.ObjectUtils;

import java.time.LocalDateTime;

/*********************************************

***********************************************/
public class InterLogNotifyMoreBean implements java.io.Serializable {

	private Long id;/*流水号;len:20*/
	private String requestNo;/*请求流水号;len:30*/
	private String serviceName;/*接口名称;len:30*/
	private Long logid;/*日志id;len:19*/
	private String className;/*处理类名;len:30*/
	private String platformNo;/*平台编码;len:30*/
	private String platformUserNo;/*平台用户id;len:30*/
	private String responseType;/*回调类型：NOTIFY 或 DIRECT_CALLBACK;len:10*/
	private String respData;/*通知的数据体;len:1800*/
	private String code;/*响应里的code;len:10*/
	private String status;/*响应里的status;len:20*/
	private String errorCode;/*存管通知处理的错误码;len:20*/
	private String errorMessage;/*存管通知处理的错误信息;len:100*/
	private String keySerial;/*证书序号;len:10*/
	private String returnStr;/*响应字符串;len:800*/
	private LocalDateTime addTime;/*添加时间;len:19*/
	private Integer doneStatus;/*处理状态，对应category=11;len:10*/
	private Integer handlerTimes;/*处理的毫秒数;len:10*/
	private String ip;/*ip;len:20*/

	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	public void setRequestNo(String requestNo){
		this.requestNo = requestNo;
	}
	public String getRequestNo(){
		return requestNo;
	}
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}
	public String getServiceName(){
		return serviceName;
	}
	public void setLogid(Long logid){
		this.logid = logid;
	}
	public Long getLogid(){
		return logid;
	}
	public void setClassName(String className){
		this.className = className;
	}
	public String getClassName(){
		return className;
	}
	public void setPlatformNo(String platformNo){
		this.platformNo = platformNo;
	}
	public String getPlatformNo(){
		return platformNo;
	}
	public void setPlatformUserNo(String platformUserNo){
		this.platformUserNo = platformUserNo;
	}
	public String getPlatformUserNo(){
		return platformUserNo;
	}
	public void setResponseType(String responseType){
		this.responseType = responseType;
	}
	public String getResponseType(){
		return responseType;
	}
	public void setRespData(String respData){
		this.respData = respData;
	}
	public String getRespData(){
		return respData;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getCode(){
		return code;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return status;
	}
	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}
	public String getErrorCode(){
		return errorCode;
	}
	public void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage(){
		return errorMessage;
	}
	public void setKeySerial(String keySerial){
		this.keySerial = keySerial;
	}
	public String getKeySerial(){
		return keySerial;
	}
	public void setReturnStr(String returnStr){
		this.returnStr = returnStr;
	}
	public String getReturnStr(){
		return returnStr;
	}
	public void setAddTime(LocalDateTime addTime){
		this.addTime = addTime;
	}
	public LocalDateTime getAddTime(){
		return addTime;
	}
	public void setDoneStatus(Integer doneStatus){
		this.doneStatus = doneStatus;
	}
	public Integer getDoneStatus(){
		return doneStatus;
	}
	public void setHandlerTimes(Integer handlerTimes){
		this.handlerTimes = handlerTimes;
	}
	public Integer getHandlerTimes(){
		return handlerTimes;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public String getIp(){
		return ip;
	}

	public String toString(){
		return  ObjectUtils.toString(this);
	}

	private static final long serialVersionUID =1950320928L;

}