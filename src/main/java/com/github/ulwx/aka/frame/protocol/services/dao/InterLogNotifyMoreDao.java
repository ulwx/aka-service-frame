package com.github.ulwx.aka.frame.protocol.services.dao;

import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogNotifyMoreBean;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

public abstract class  InterLogNotifyMoreDao  extends AkaDaoSupport {

	public abstract  int insert(InterLogNotifyBean notify) throws Exception ;
	public abstract  void update(InterLogNotifyMoreBean notify) throws Exception;
	public abstract InterLogNotifyMoreBean getOne(String requestNo, String responseType, String serviceName) throws Exception;


}
