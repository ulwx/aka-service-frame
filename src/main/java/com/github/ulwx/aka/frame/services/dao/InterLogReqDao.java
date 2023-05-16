package com.github.ulwx.aka.frame.services.dao;

import com.github.ulwx.aka.frame.services.domain.InterLogReqBean;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

public abstract class InterLogReqDao  extends AkaDaoSupport {

	public abstract InterLogReqBean getInterLogReq(String reqNo,String servieName) throws Exception;
	public abstract void update(InterLogReqBean logreq) throws Exception ;
	public abstract long insert(InterLogReqBean req) throws Exception;
	
}
