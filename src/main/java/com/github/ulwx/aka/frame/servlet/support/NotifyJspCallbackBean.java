package com.github.ulwx.aka.frame.servlet.support;

import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.utils.IError;

/**
 * 第三方回调请求时返回页面，根据请求可以请求重定向
 */
public class NotifyJspCallbackBean extends CallbackBean{
	public Data data=new Data();

	public static class Data{
		@Comment("操作类型,用于接收端区分")
		public String operType="";
		@Comment("扩展信息")
		public Object ext;
	}

	public Data getData() {
		return data;
	}
	public static NotifyJspCallbackBean SUC(String message) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(message);
		cb.setStatus(1);
		return cb;
	}
	public static NotifyJspCallbackBean SUC(String message, String operType) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(message);
		cb.setStatus(1);
		cb.data.operType=operType;
		return cb;
	}
	public static NotifyJspCallbackBean SUC() {
		return SUC("成功");
	}
	
	
	public static NotifyJspCallbackBean ERR(String message) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(message);
		cb.setError(IError.COMMON_ERROR);
		cb.setStatus(0);
		return cb;
	}
	
	public static NotifyJspCallbackBean ERR(String message, String operType) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(message);
		cb.setError(IError.COMMON_ERROR);
		cb.data.operType=operType;
		cb.setStatus(0);
		return cb;
	}
	public static NotifyJspCallbackBean ERR(int errorCode) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(IError.get(errorCode));
		cb.setError(errorCode);;
		cb.setStatus(0);
		return cb;
	}
	
	public static NotifyJspCallbackBean ERR(int errorCode, String msg) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(IError.get(errorCode,msg));
		cb.setError(errorCode);;
		cb.setStatus(0);
		return cb;
	}
	public static NotifyJspCallbackBean ERR(int errorCode, String msg, String operType) {
		NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
		cb.setMessage(IError.get(errorCode,msg));
		cb.setError(errorCode);
		cb.data.operType=operType;
		cb.setStatus(0);
		return cb;
	}
}
