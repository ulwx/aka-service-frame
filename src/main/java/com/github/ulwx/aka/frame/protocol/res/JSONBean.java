package com.github.ulwx.aka.frame.protocol.res;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.ExchangeException;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public class JSONBean extends IUFrameBean<JSONBean> {


	public static JSONBean SUC() {

		return SUC("处理成功！");
	}

	
	public static JSONBean SUC(String msg) {
		// TODO Auto-generated method stub
		JSONBean bean = new JSONBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		return bean;
	}

	public static <T extends  JSONBean> T ERROR(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		} else {

		}
		bean.setStatus(Protocol.Status.FAIL);
		return bean;
	}

	public static JSONBean ERROR(ExchangeException e) {
		// TODO Auto-generated method stub
		JSONBean bean = new JSONBean();
		bean.setMessage(e.getMessage());
		bean.setStatus(0);
		bean.setError(e.getCode());
		return bean;
	}

	public static JSONBean ERROR(String msg) {
		// TODO Auto-generated method stub
		JSONBean bean = new JSONBean();
		bean.setMessage(IError.get(IError.COMMON_ERROR, msg));
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		return bean;
	}


	public static JSONBean ERROR(int errorCode) {
		// TODO Auto-generated method stub
		JSONBean bean = new JSONBean();
		bean.setMessage(IError.get(errorCode));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}

	public static JSONBean ERROR(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		JSONBean bean = new JSONBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}

	public static <T extends  JSONBean> T  SUC(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理成功!");
		} else {
			////
		}

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
