package com.github.ulwx.aka.frame.protocol.services.dao.impl;

import com.github.ulwx.aka.dbutils.database.DbContext;
import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogNotifyDao;
import com.github.ulwx.aka.frame.protocol.services.dao.impl.model.InterLogNotify;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyBean;


public class InterLogNotifyDaoImpl extends InterLogNotifyDao{

	@Override
	public  String getDS(){
		AkaFrameProperties akaFrameProperties=this.beanGet.bean(AkaFrameProperties.class);
		String poolName="";
		if(akaFrameProperties.getStorage().getDatabse().getDs()!=null){
			poolName=akaFrameProperties.getStorage().getDatabse().getDs();
			return poolName;
		}else{
			throw new RuntimeException("没有指定数据源名称！");
		}

	}
	public  int insert(InterLogNotifyBean notify) throws Exception {


		if(notify.getRespData()!=null && notify.getRespData().length()>1350) {
			notify.setRespData(notify.getRespData().substring(0, 1300)+"...");
		}
		if (notify.getErrorMessage()!=null && notify.getErrorMessage().length() > 100) {
			notify.setErrorMessage(notify.getErrorMessage().substring(0, 97) + "...");
		}
		DbContext.setReflectClass(InterLogNotify.class);
		return (int) this.getTemplate().insertReturnKeyBy(notify);

	}
	

	
	public  void update(InterLogNotifyBean notify) throws Exception{

		if(notify.getRespData()!=null && notify.getRespData().length()>1350) {
			notify.setRespData(notify.getRespData().substring(0, 1300)+"...");
		}
		if (notify.getErrorMessage()!=null && notify.getErrorMessage().length() > 100) {
			notify.setErrorMessage(notify.getErrorMessage().substring(0, 97) + "...");
		}
		DbContext.setReflectClass(InterLogNotify.class);
		this.getTemplate().updateBy( notify, MD.of("id"));

	}



	@Override
	public InterLogNotifyBean getOne(String requestNo, String serviceName) throws Exception {

		// TODO Auto-generated method stub
		InterLogNotifyBean bean= new InterLogNotifyBean();
		bean.setRequestNo(requestNo);
		bean.setServiceName(serviceName);
		DbContext.setReflectClass(InterLogNotify.class);
		return this.getTemplate().queryOneBy(bean);

	}



	
	
}
