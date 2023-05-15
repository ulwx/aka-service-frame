package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.annotation.InterfaceTest;


public abstract class ProMobileBase extends Protocol{
	
	@InterfaceTest(value = "22")
	@Comment("用户id")
	public String userid;
	
	@InterfaceTest(value = "18565574709")
	@Comment("手机号码")
	public String phone;
	
	@InterfaceTest(value = "deviceid_12345")
	@Comment("设备序列号或设备id")
	public String udid;//设备序列号或设备id

	
	@InterfaceTest(value = "F9V3")
	@Comment("设备型号")
	public String model;//设备型号
	

	@InterfaceTest("com.hd.kangaroo.thememarket")
	@Comment("App的名称")
	public String appName;//App的名称
	
	@InterfaceTest("app的版本号")
	public String appVer;//
	
	@Comment("用户端设备的系统版本")
	public String sysVersion;//用户端设备的系统版本
	
	@Comment("平台")
	public String sysPlatform;//
	

	public  String getModel(){ 
		return this.model;
	}
	


	public  String getAppname(){
		return this.appName;
	}
	public  String getAppver(){
		return this.appVer;
	}


	public String getDeviceid() {
		return this.udid;
	}

	public  String getUserid(){//用户id
		if(this.userid==null) return "";
		return this.userid;
	}
	
	public String getPhone() {
		// TODO Auto-generated method stub
		if(this.phone==null) return "";
		return this.phone;
	}
}
