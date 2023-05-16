package com.github.ulwx.aka.frame.servlet;

import com.github.ulwx.aka.frame.protocol.CallBackType;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.ulwx.tool.StringUtils;

public class ReqCodeParm {

	//协议版本号
	private String ver = "";
	//协议模块名称
	private String moduleName = "";
	//解析编码，对应aka-service-frame.properties里的notify.req.parser.[parseCode]配置，用于指定请求的解析插件
	private String parseCode = "";
	//0:没有回调  1:异步回调  2:网关回调  3:本地提交回调
	private CallBackType callBackType = CallBackType.NO_CALLBACK;
	//服务名称，每个服务对应一个协议处理类
	private String serviceName = "";
	//扩展字符串
	private String extStr="";
	//协议请求的命名空间
	private String namespace="";

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public static ReqCodeParm parse(String reqCodeStr) {
		String[] strs = reqCodeStr.split("@",7);//reqCodeStr.split("\\_");
		ReqCodeParm nrc = new ReqCodeParm();
		nrc.ver = strs[0];
		nrc.moduleName = strs[1];
		nrc.parseCode = strs[2];
		nrc.callBackType = CallBackType.valueFrom(strs[3]);
		nrc.serviceName = strs[4];
		nrc.setExtStr(strs[5]);
		nrc.setNamespace(strs[6]);
		return nrc;

	}
	
	public static void main(String[] args) {
		String s="v1@trade@jjs@1@CUSTOMER_REGISTER@@intf";
		ReqCodeParm rcp=parse(s);
		int i=0;
	}
	public ReqCodeParm(String serviceName) {
		this.serviceName=serviceName;
	}

	private ReqCodeParm(String parseCode, CallBackType callBackType, String serviceName,String extStr) {
		this.parseCode=StringUtils.trim(parseCode);
		this.callBackType=callBackType;
		this.serviceName=StringUtils.trim(serviceName);
		this.extStr=StringUtils.trim(extStr);
	}
	public ReqCodeParm(Protocol pro, String parseCode, CallBackType cllBackType, String serviceName, String extStr) {
		this(parseCode, cllBackType, serviceName,extStr);
		this.ver=StringUtils.trim(pro.ver);
		this.moduleName=StringUtils.trim(pro.moduleName);
		this.namespace=StringUtils.trim(pro.namespace);
		
	}
	public  String toReqCodeString() {
		return toReqCodeString(ver, moduleName, parseCode, callBackType, serviceName,this.extStr,this.namespace);
	}
	public static String toReqCodeString(String ver, String moduleName, String parseCode,
										 CallBackType callBackType,
			String serviceName,String extStr,String infKey) {
		return ver + "@" + moduleName + "@" + parseCode + "@" + callBackType + "@" + serviceName+"@"+extStr+"@"+infKey;
	}


	public String getExtStr() {
		return extStr;
	}
	public void setExtStr(String extStr) {
		this.extStr = extStr;
	}
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getParseCode() {
		return parseCode;
	}

	public void setParseCode(String parseCode) {
		this.parseCode = parseCode;
	}

	public CallBackType getCallType() {
		return callBackType;
	}

	public void setCallType(CallBackType callBackType) {
		this.callBackType = callBackType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ReqCodeParm() {
		// TODO Auto-generated constructor stub
	}

}
