package com.github.ulwx.aka.frame.protocol.res;

import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;
import com.ulwx.tool.EscapeUtil;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.StringUtils;

import java.util.HashMap;
import java.util.Map;

public  class RedirectBean  extends IUFrameBean<RedirectBean> {
	
	private String redirectURL;
	private Object data;
	
	public String getRedirectURL() {
		return redirectURL;
	}

	@Override
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	
	public static RedirectBean SUC(String msg, String redirectUrl) {
		RedirectBean bean = new NoDataRedirectBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		bean.redirectURL = redirectUrl;
		return wrapRedirectUrl(bean);
	}

	public static RedirectBean ERROR(int errorCode, String errorMsg, String redirectUrl) {
		RedirectBean bean = new NoDataRedirectBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.redirectURL = redirectUrl;
		return wrapRedirectUrl(bean);
	}
	public static RedirectBean ERROR(String errorMsg, String redirectUrl) {
		RedirectBean bean = new NoDataRedirectBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		bean.redirectURL = redirectUrl;
		return wrapRedirectUrl(bean);
	}

	public static RedirectBean wrapRedirectUrl(RedirectBean bean){

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
	public static <T extends RedirectBean > T ERROR(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		} else {

		}
		bean.setStatus(Protocol.Status.FAIL);
		return (T)wrapRedirectUrl(bean);
	}



	@SuppressWarnings("unchecked")
	public static <T extends RedirectBean > T SUC(T bean) {
		

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


	private static class NoDataRedirectBean extends RedirectBean{

		@Override
		public Object getData() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		RedirectBean rb=new NoDataRedirectBean();
		rb.redirectURL="http://www.baidu.com";
		rb.setStatus(1);
		rb.setMessage("xxx");;
		System.out.println(ObjectUtils.toString(RedirectBean.SUC(rb)));
		
		
	}
	
	

	
}
