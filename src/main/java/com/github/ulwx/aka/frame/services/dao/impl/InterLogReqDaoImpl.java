package com.github.ulwx.aka.frame.services.dao.impl;

import com.github.ulwx.aka.dbutils.database.DbContext;
import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.frame.services.domain.InterLogReqBean;
import com.ulwx.tool.StringUtils;

import java.time.LocalDateTime;

public class InterLogReqDaoImpl extends InterLogReqDao {
	@Override
	public  String getDS(){
		UIFrameAppConfig akaFrameProperties=this.beanGet.bean(UIFrameAppConfig.class);
		String poolName="";
		if(StringUtils.hasText(akaFrameProperties.getAkaFrameProperties().getDsName())){
			poolName=akaFrameProperties.getAkaFrameProperties().getDsName();
			return poolName;
		}else{
			throw new RuntimeException("没有指定数据源名称！");
		}

	}
	public  InterLogReqBean getInterLogReq(String reqNo,String servieName) throws Exception{
		throw new UnsupportedOperationException();

	}
	
	public  void update(InterLogReqBean logreq) throws Exception {
		DbContext.setReflectClass(InterLogReq.class);
		this.template.updateBy( logreq, MD.of("id"));
	}
	public  long insert(InterLogReqBean req) throws Exception{
		req.setAddTime(LocalDateTime.now());
		if(req.getReqData()!=null && req.getReqData().length()>1980) {
			req.setReqData(req.getReqData().substring(0, 1980)+"...");
		}
		DbContext.setReflectClass(InterLogReq.class);
		return this.template.insertReturnKeyBy(req);
	}



	
	
	
	
}
