package com.github.ulwx.aka.frame.protocol.res;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.ExchangeException;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public class STRBean extends  IUFrameBean<STRBean> {
	
	
	public Data data=new Data();

	public static class Data{
		@Comment("返回的字符串")
		public String retrunString;
		
	}
	
	
	public static  STRBean ERROR(int errorCode) {
		STRBean bean=new STRBean();
		bean.setMessage(IError.get(errorCode));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	public static STRBean ERROR(int errorCode,String errorMsg) {
		STRBean bean=new STRBean();
		bean.setMessage(IError.get(errorCode,errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	
	public static STRBean SUC(String msg) {
		STRBean bean=new STRBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		return bean;
	}

	public static STRBean SUC() {
		// TODO Auto-generated method stub
		return SUC("处理成功！");
	}
	
	public static <T extends STRBean> T ERROR(T bean) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		}else {
			bean.setMessage(bean.getMessage());
		}
		
		bean.setStatus(Protocol.Status.FAIL);
		return bean;
	}
	
	public static STRBean ERROR(ExchangeException e) {
		// TODO Auto-generated method stub
		STRBean bean=new STRBean();
		bean.setMessage(e.getMessage());
		bean.setStatus(0);
		bean.setError(e.getCode());
		return bean;
	}
	
	public static STRBean ERROR(String msg) {
		// TODO Auto-generated method stub
		STRBean bean=new STRBean();
		bean.setMessage(IError.get(IError.COMMON_ERROR,msg));
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		return bean;
	}
	
	public static STRBean ERROR_MSG(int errorCode, String errMsg) {
		// TODO Auto-generated method stub
		STRBean bean=new STRBean();
		bean.setMessage(errMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}

	
	public  static <T extends STRBean> T SUC(T bean) {
		// TODO Auto-generated method stub
		bean.setError(0);
		bean.setStatus(Protocol.Status.SUCCESS);
		return bean;
	}
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
}
