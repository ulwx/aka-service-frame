package com.github.ulwx.aka.frame.services.domain;

import com.github.ulwx.aka.dbutils.database.annotation.AkaColumn;
import com.github.ulwx.aka.dbutils.tool.support.ObjectUtils;

import java.time.LocalDateTime;

/*********************************************

***********************************************/
public class InterLogReqBean implements java.io.Serializable {

	private Long id;/*流水号;len:20*/
	private String requestNo;/*业务请求订单号，协议对象里的业务请求订单号;len:30*/
	private String type;/*接口类型;len:20*/
	private String className;/*处理类;len:50*/
	private String responseType;/*回调类型：回调类型：异步回调 , 网关回调;len:20*/
	private String serviceName;/*接口名称;len:50*/
	private String classReqArgs;/*处理类请求参数;len:1000*/
	private String platformNo;/*平台编码;len:30*/
	private String userDevice;/*用户终端设备类型;len:80*/
	private String platformUserNo;/*平台用户id;len:30*/
	private String reqData;/*请求数据体;len:2000*/
	private String errorCode;/*请求里的错误码;len:20*/
	private String errorMessage;/*请求里的的错误信息;len:100*/
	private String returnStr;/*响应字符串;len:500*/
	private String extStr;/*扩展字符串;len:250*/
	private Integer doneStatus;/*响应的处理状态;len:10*/
	private String code;/*响应里的状态码;根据业务定义。len:10*/
	private String status;/*响应里的状态;len:20*/
	private Long logid;/*日志id;len:19*/
	private LocalDateTime addTime;/*添加时间;len:19*/
	private String refReqNo;/*关联请求流水号;len:30*/
	private Integer handlerTimes;/*处理的毫秒数;len:10*/
	private String ip;/*ip;len:20*/

	public void setId(Long id){
		this.id = id;
	}
	@AkaColumn(isAutoincrement=true,isNullable=false)
	public Long getId(){
		return id;
	}
	public void setRequestNo(String requestNo){
		this.requestNo = requestNo;
	}

	public String getRequestNo(){
		return requestNo;
	}
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
	public void setClassName(String className){
		this.className = className;
	}

	public String getClassName(){
		return className;
	}
	public void setResponseType(String responseType){
		this.responseType = responseType;
	}

	public String getResponseType(){
		return responseType;
	}
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

	public String getServiceName(){
		return serviceName;
	}
	public void setClassReqArgs(String classReqArgs){
		this.classReqArgs = classReqArgs;
	}

	public String getClassReqArgs(){
		return classReqArgs;
	}
	public void setPlatformNo(String platformNo){
		this.platformNo = platformNo;
	}

	public String getPlatformNo(){
		return platformNo;
	}
	public void setUserDevice(String userDevice){
		this.userDevice = userDevice;
	}

	public String getUserDevice(){
		return userDevice;
	}
	public void setPlatformUserNo(String platformUserNo){
		this.platformUserNo = platformUserNo;
	}

	public String getPlatformUserNo(){
		return platformUserNo;
	}
	public void setReqData(String reqData){
		this.reqData = reqData;
	}

	public String getReqData(){
		return reqData;
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
	public void setReturnStr(String returnStr){
		this.returnStr = returnStr;
	}

	public String getReturnStr(){
		return returnStr;
	}
	public void setExtStr(String extStr){
		this.extStr = extStr;
	}

	public String getExtStr(){
		return extStr;
	}
	public void setDoneStatus(Integer doneStatus){
		this.doneStatus = doneStatus;
	}

	public Integer getDoneStatus(){
		return doneStatus;
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
	public void setLogid(Long logid){
		this.logid = logid;
	}

	public Long getLogid(){
		return logid;
	}
	public void setAddTime(LocalDateTime addTime){
		this.addTime = addTime;
	}

	public LocalDateTime getAddTime(){
		return addTime;
	}
	public void setRefReqNo(String refReqNo){
		this.refReqNo = refReqNo;
	}

	public String getRefReqNo(){
		return refReqNo;
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

}