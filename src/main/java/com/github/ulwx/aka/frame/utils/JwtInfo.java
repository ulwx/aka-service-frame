package com.github.ulwx.aka.frame.utils;

public class JwtInfo {

	public static String JWT_ID="id";
	public static String USER="user";
	public static String PHONE="phone";
	public static String DEVICE_ID="udid";
	public static String USER_TYPE="userType";
	public static String JWT_EXT="jwtExt";
	private String jwtID;
	private String user;//存放为用户id，最终会放入请求Bean的userid字段
	private String phone;
	private String userType;
	private String ext;
	private String deviceID;

	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getJwtID() {
		return jwtID;
	}
	public void setJwtID(String jwtID) {
		this.jwtID = jwtID;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	
	
	
	
}
