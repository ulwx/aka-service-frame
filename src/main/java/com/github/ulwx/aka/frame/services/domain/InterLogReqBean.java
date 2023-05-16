package com.github.ulwx.aka.frame.services.domain;

import com.ulwx.tool.ObjectUtils;

import java.time.LocalDateTime;

/*********************************************

***********************************************/
public class InterLogReqBean implements java.io.Serializable {

	private Long id;/*流水号;len:20*/
	private String requestNo;/*请求流水号;len:30*/
	private String type;/*分：网关接口，直连接口，对账下载;len:6*/
	private String reqCode;/*reqCode区分接口逻辑，[ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace];len:100*/
	private String className;/*处理类;len:30*/
	private String serviceName;/*接口名称;len:30*/
	private String classReqArgs;/*处理类请求参数;len:1000*/
	private String platformNo;/*平台编码;len:30*/
	private String userDevice;/*用户终端设备类型;len:80*/
	private String platformUserNo;/*平台用户id;len:30*/
	private String reqData;/*请求数据体;len:2000*/
	private String keySerial;/*证书序号;len:10*/
	private String returnStr;/*响应字符串;len:500*/
	private String extStr;/*扩展字符串;len:250*/
	private Integer doneStatus;/*处理状态，对应category=11;len:10*/
	private String status;/*接口返回的状态;len:20*/
	private Long logid;/*日志id;len:19*/
	private LocalDateTime addTime;/*添加时间;len:19*/
	private String refReqNo;/*关联请求流水号;len:30*/
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
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public void setReqCode(String reqCode){
		this.reqCode = reqCode;
	}
	public String getReqCode(){
		return reqCode;
	}
	public void setClassName(String className){
		this.className = className;
	}
	public String getClassName(){
		return className;
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

	private static final long serialVersionUID =170849587L;

}