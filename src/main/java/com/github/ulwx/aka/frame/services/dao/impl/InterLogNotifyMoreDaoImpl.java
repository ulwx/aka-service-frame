package com.github.ulwx.aka.frame.services.dao.impl;

import com.github.ulwx.aka.dbutils.database.DbContext;
import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.services.dao.InterLogNotifyMoreDao;
import com.github.ulwx.aka.frame.services.dao.impl.model.InterLogNotifyMore;
import com.github.ulwx.aka.frame.services.domain.InterLogNotifyMoreBean;

public class InterLogNotifyMoreDaoImpl extends InterLogNotifyMoreDao {
	@Override
	public  String getDS(){
		UIFrameAppConfig akaFrameProperties=this.beanGet.bean(UIFrameAppConfig.class);
		String poolName="";
		if(akaFrameProperties.getStorage().getDatabse().getDs()!=null){
			poolName=akaFrameProperties.getStorage().getDatabse().getDs();
			return poolName;
		}else{
			throw new RuntimeException("没有指定数据源名称！");
		}

	}

	public  int insert(InterLogNotifyMoreBean notify) throws Exception {

		InterLogNotifyMoreBean nm=new InterLogNotifyMoreBean();
		
		nm.setAddTime(notify.getAddTime());
		nm.setClassName(notify.getClassName());
		nm.setCode(notify.getCode());
		nm.setDoneStatus(notify.getDoneStatus());
		nm.setErrorCode(notify.getErrorCode());
		nm.setErrorMessage(notify.getErrorMessage());
		nm.setHandlerTimes(notify.getHandlerTimes());
		nm.setLogid(notify.getLogid());
		nm.setPlatformNo(notify.getPlatformNo());
		nm.setPlatformUserNo(notify.getPlatformUserNo());
		nm.setRequestNo(notify.getRequestNo());
		nm.setRespData(notify.getRespData());
		nm.setResponseType(notify.getResponseType());
		nm.setReturnStr(notify.getReturnStr());
		nm.setServiceName(notify.getServiceName());
		nm.setIp(notify.getIp());
		nm.setStatus(notify.getStatus());
	
		if(nm.getRespData()!=null && nm.getRespData().length()>1350) {
			nm.setRespData(nm.getRespData().substring(0, 1300)+"...");
		}
		return (int) this.getTemplate().insertReturnKeyBy(nm);

	}
	
	public  void update(InterLogNotifyMoreBean notify) throws Exception{

		if(notify.getReturnStr()!=null && notify.getReturnStr().length()>790) {
			notify.setReturnStr(notify.getReturnStr().substring(0, 790)+"...");
		}

		DbContext.setReflectClass(InterLogNotifyMore.class);
		this.template.updateBy( notify, MD.of("id"));

	}
	

	@Override
	public InterLogNotifyMoreBean getOne(String requestNo, String responseType, String serviceName) throws Exception {

		InterLogNotifyMoreBean bean= new InterLogNotifyMoreBean();
		bean.setRequestNo(requestNo);
		bean.setServiceName(serviceName);
		bean.setResponseType(responseType);
		DbContext.setReflectClass(InterLogNotifyMore.class);

		return this.template.queryOneBy(bean);

	}

}
