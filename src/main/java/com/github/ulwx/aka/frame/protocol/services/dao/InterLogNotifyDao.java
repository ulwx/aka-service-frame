package com.github.ulwx.aka.frame.protocol.services.dao;

import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyBean;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

public abstract class InterLogNotifyDao extends AkaDaoSupport {

	public abstract  int insert(InterLogNotifyBean notify) throws Exception ;
	public abstract void update(InterLogNotifyBean notify) throws Exception;
	public abstract InterLogNotifyBean getOne(String requestNo,String serviceName)throws Exception;

}
