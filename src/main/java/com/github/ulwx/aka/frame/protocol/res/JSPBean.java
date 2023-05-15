package com.github.ulwx.aka.frame.protocol.res;

import java.util.Map;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public class JSPBean extends IUFrameBean<JSPBean> {

	
	public Data data = new Data();

	public static class Data {
		@Comment("post提交的的参数")
		public Map<String, String> postParam;
		@Comment("post提交的URL")
		public String postURL;
		@Comment("回调URL")
		public String redirectUrl;
		public String operType = "";
	}

	
	public static JSPBean SUC() {
		throw new UnsupportedOperationException();
	}
	
	public static JSPBean SUC(String msg) {
		throw new UnsupportedOperationException();
	}
	
	
	public static <T extends  JSPBean> T  SUC(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理成功!");
		} else {
			////
		}

		bean.setStatus(1);
		bean.setError(0);
		return bean;
	}
	
	
	
	public  static JSPBean SUC(String msg, String redirectUrl) {
		JSPBean bean = new JSPBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		bean.data.redirectUrl = redirectUrl;
		return bean;
	}

	public static JSPBean SUC(String msg, String redirectUrl, String operType) {
		JSPBean bean = new JSPBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);

		bean.data.redirectUrl = redirectUrl;
		bean.data.operType = operType;

		return bean;
	}


	public static JSPBean ERROR(int errorCode, String errorMsg, String redirectUrl) {
		JSPBean bean = new JSPBean();
		bean.setMessage(IError.get(errorCode, errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.data.redirectUrl = redirectUrl;
		return bean;
	}
	public static JSPBean ERROR(String errorMsg, String redirectUrl) {
		JSPBean bean = new JSPBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		bean.data.redirectUrl = redirectUrl;
		return bean;
	}
	public static JSPBean ERROR(int errorCode, String errorMsg, String redirectUrl, String operType) {
		JSPBean bean = new JSPBean();
		bean.setMessage(IError.get(errorCode, errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.data.operType = operType;
		bean.data.redirectUrl = redirectUrl;
		return bean;
	}

	public Data getData() {
		return data;
	}
}
