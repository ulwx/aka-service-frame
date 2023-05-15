package com.github.ulwx.aka.frame.protocol.res;

import java.io.File;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.ExchangeException;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public class DownLoadBean extends  IUFrameBean<DownLoadBean> {
	
	
	public Data data=new Data();

	public static class Data{
		@Comment("返回的字符串")
		public String fileName;
		public File file;

	}
	
	public String getFileName() {
		return data.fileName;
	}
	public void setFileName(String fileName) {
		this.data.fileName = fileName;
	}
	public File getFile() {
		return data.file;
	}
	public void setFile(File file) {
		this.data.file = file;
	}
	
	
	public static  DownLoadBean ERROR(int errorCode) {
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(IError.get(errorCode));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	public static DownLoadBean ERROR(int errorCode,String errorMsg) {
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(IError.get(errorCode,errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}
	
	public static DownLoadBean SUC(String msg) {
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		return bean;
	}

	public static DownLoadBean SUC() {
		// TODO Auto-generated method stub
		return SUC("处理成功！");
	}
	
	public static <T extends DownLoadBean> T ERROR(T bean) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理失败!");
		}else {
			bean.setMessage(bean.getMessage());
		}
		
		bean.setStatus(Protocol.Status.FAIL);
		return bean;
	}
	
	public static DownLoadBean ERROR(ExchangeException e) {
		// TODO Auto-generated method stub
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(e.getMessage());
		bean.setStatus(0);
		bean.setError(e.getCode());
		return bean;
	}
	
	public static DownLoadBean ERROR(String msg) {
		// TODO Auto-generated method stub
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(IError.get(IError.COMMON_ERROR,msg));
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		return bean;
	}
	
	public static DownLoadBean ERROR_MSG(int errorCode, String errMsg) {
		// TODO Auto-generated method stub
		DownLoadBean bean=new DownLoadBean();
		bean.setMessage(errMsg);
		bean.setStatus(0);
		bean.setError(errorCode);
		return bean;
	}

	
	public  static <T extends DownLoadBean> T SUC(T bean) {
		// TODO Auto-generated method stub
		bean.setError(0);
		bean.setStatus(Protocol.Status.SUCCESS);
		return bean;
	}
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return data;
	}
}
