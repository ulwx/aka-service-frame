package com.github.ulwx.aka.frame.protocol.res;

import com.ulwx.tool.EscapeUtil;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;

import java.util.HashMap;
import java.util.Map;

public abstract class ForwardBean  extends IUFrameBean<ForwardBean> {
	
	private String redirectURL;
	
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	
	public String getRedirectURL() {
		return redirectURL;
	}


	public static ForwardBean SUC(String msg, String redirectURL) {
		ForwardBean bean = new NoDataForwardBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		bean.redirectURL = redirectURL;
		return wrapRedirectUrl(bean);
	}

	public static ForwardBean ERROR(int errorCode, String errorMsg, String redirectUrl) {
		ForwardBean bean = new NoDataForwardBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.redirectURL = redirectUrl;
		return wrapRedirectUrl(bean);
	}
	public static ForwardBean ERROR(String errorMsg, String redirectUrl) {
		ForwardBean bean = new NoDataForwardBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		bean.redirectURL = redirectUrl;
		return wrapRedirectUrl(bean);
	}

	public static ForwardBean wrapRedirectUrl(ForwardBean bean){

		if(StringUtils.isEmpty(bean.redirectURL)){
			return bean;
		}else {
			
		}
		Map<String,Object> map=new HashMap<>();
		map.put("status", bean.getStatus());
		map.put("error", bean.getError());
		map.put("message", bean.getMessage());
		if(bean.getData()!=null) {
			map.put("data", bean.getData());
		}
		if(bean.redirectURL.lastIndexOf("?")>=0){

			bean.redirectURL=bean.redirectURL+"&ret="+ EscapeUtil.escapeUrl(ObjectUtils.toJsonString(map),"utf-8");

		}else{
			bean.redirectURL=bean.redirectURL+"?ret="+ EscapeUtil.escapeUrl(ObjectUtils.toJsonString(map),"utf-8");

		}
		return bean;

	}

	@SuppressWarnings("unchecked")
	public static <T extends ForwardBean > T ERROR(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		} else {

		}
		bean.setStatus(Protocol.Status.FAIL);
		return (T)wrapRedirectUrl(bean);
	}



	@SuppressWarnings("unchecked")
	public static <T extends ForwardBean > T SUC(T bean) {
		

		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理成功!");
		} else {
			////
		}
		bean.setError(0);
		bean.setStatus(Protocol.Status.SUCCESS);
		return (T)wrapRedirectUrl(bean);
	}


//	@Override
//	public Object getData() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public static void main(String[] args) {
		ForwardBean rb=new NoDataForwardBean();
		rb.redirectURL="http://www.baidu.com";
		rb.setStatus(1);
		rb.setMessage("xxx");;
		System.out.println(ObjectUtils.toString(ForwardBean.SUC(rb)));
		
		
	}
	
	private static class NoDataForwardBean extends ForwardBean{

		@Override
		public Object getData() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	
}
