package com.github.ulwx.aka.frame.protocol.res;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.ExchangeException;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public  class BaseResBean extends IUFrameBean<BaseResBean>{

	
	public static BaseRes SUC()
	{
		BaseRes bean=new NullDataRes();
		bean.setMessage("处理成功！");
		bean.setError(0);
		bean.setStatus(Protocol.Status.SUCCESS);
		return bean;
	}
	
	public static   BaseRes SUC(String msg)
	{
		BaseRes bean=new NullDataRes();
		bean.setMessage(msg);
		bean.setError(0);
		bean.setStatus(Protocol.Status.SUCCESS);
		return bean;
	}

	public  static <T extends BaseResBean> T  ERROR( T bean)
	{
		if(StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		}else {
			bean.setMessage(bean.getMessage());
		}
		
		bean.setError(IError.COMMON_ERROR);
		bean.setStatus(Protocol.Status.FAIL);
		return bean;
	}
	public static  BaseRes ERROR(ExchangeException e) 
	{
		BaseRes bean=new BaseResBean();
		bean.setMessage(e.getMessage());
		bean.setStatus(0);
		bean.setError(e.getCode());
		return bean;
	}
	public static  BaseRes ERROR(String msg)
	{
		BaseRes bean=new BaseResBean();
		bean.setMessage(IError.get(IError.COMMON_ERROR,msg));
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		return bean;
	}
	
	public static  BaseRes ERROR_MSG(int errorCode,String errMsg)
	{
		BaseRes bean=new BaseResBean();
		bean.setMessage(errMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	public static  BaseRes ERROR(int errorCode)
	{
		BaseRes bean=new BaseResBean();
		bean.setMessage(IError.get(errorCode));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	public static  BaseRes ERROR(int errorCode,String errorMsg)
	{
		BaseRes bean=new BaseResBean();
		bean.setMessage(IError.get(errorCode,errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	

	public static  <T extends BaseResBean> T SUC(T bean)
	{
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
