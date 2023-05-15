package com.github.ulwx.aka.frame.protocol.services.dao.impl;

import com.github.ulwx.aka.dbutils.database.DbContext;
import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.services.dao.InterLogReqDao;
import com.github.ulwx.aka.frame.protocol.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class InterLogReqDaoImpl extends InterLogReqDao {
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
	public  InterLogReqBean getInterLogReq(String reqNo,String servieName) throws Exception{
		Map<String,Object> args=new HashMap<>();
		args.put("reqNo", reqNo);
		args.put("servieName", servieName);

		return this.template.queryOne(InterLogReqBean.class, MD.md(),args);

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
