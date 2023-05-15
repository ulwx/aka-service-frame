package com.github.ulwx.aka.frame.protocol.utils;

import java.util.HashMap;
import java.util.Map;

import com.ulwx.tool.StringUtils;

public class IError {
	
	public static int COMMON_ERROR=1000;
	public static int BG_ERROR=1001;
	public static int JWT_VERIFY_FAIL=1002;
	public static Map<Integer,String> errors=new HashMap<Integer,String>();
	static {
		errors.put(COMMON_ERROR, "");
		errors.put(BG_ERROR, "后台处理失败！");
		errors.put(JWT_VERIFY_FAIL, "用户验证失败！");
	}
	
	public Map<Integer, String> getError() {
		// TODO Auto-generated method stub
		return errors;
	}
	
	public static String get(int code,String arg) {
		 return errors.get(code)+ ((StringUtils.hasText(arg))?""+arg :"");
	}
	public static String get(int code) {
		return String.format(errors.get(code));
	}
	public static void  put(Map<Integer,String> map){
		errors.putAll(map);
	}
}
