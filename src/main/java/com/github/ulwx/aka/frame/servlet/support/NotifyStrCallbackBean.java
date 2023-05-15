package com.github.ulwx.aka.frame.servlet.support;

import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.utils.IError;

/**
 * 第三方回调请求时进行同步响应，根据响应的状态(status)如果为成功，则响应data.sucTxt，否则响应data.failTxt
 */
public class NotifyStrCallbackBean extends CallbackBean{
	public Data data=new Data();

	public static class Data{
		@Comment("操作类型,用于接收端区分")
		public String operType="";
		@Comment("第三方异步请求的成功处理后的响应消息")
		public String sucTxt="SUCCESS";
		@Comment("第三方异步请求的处理失败后的响应消息")
		public String failTxt="FAIL";
		@Comment("扩展信息")
		public Object ext;
	}

	public Data getData() {
		return data;
	}
	public static NotifyStrCallbackBean SUC(String message) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(message);
		cb.setStatus(1);
		return cb;
	}
	public static NotifyStrCallbackBean SUC(String message, String operType) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(message);
		cb.setStatus(1);
		cb.data.operType=operType;
		return cb;
	}
	public static NotifyStrCallbackBean SUC() {
		return SUC("成功");
	}
	
	
	public static NotifyStrCallbackBean ERR(String message) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(message);
		cb.setError(IError.COMMON_ERROR);
		cb.setStatus(0);
		return cb;
	}
	
	public static NotifyStrCallbackBean ERR(String message, String operType) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(message);
		cb.setError(IError.COMMON_ERROR);
		cb.data.operType=operType;
		cb.setStatus(0);
		return cb;
	}
	public static NotifyStrCallbackBean ERR(int errorCode) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(IError.get(errorCode));
		cb.setError(errorCode);;
		cb.setStatus(0);
		return cb;
	}
	
	public static NotifyStrCallbackBean ERR(int errorCode, String msg) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(IError.get(errorCode,msg));
		cb.setError(errorCode);;
		cb.setStatus(0);
		return cb;
	}
	public static NotifyStrCallbackBean ERR(int errorCode, String msg, String operType) {
		NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
		cb.setMessage(IError.get(errorCode,msg));
		cb.setError(errorCode);
		cb.data.operType=operType;
		cb.setStatus(0);
		return cb;
	}
}
