package com.github.ulwx.aka.frame.services.dao;

import com.github.ulwx.aka.frame.services.domain.InterLogNotifyMoreBean;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

public abstract class  InterLogNotifyMoreDao  extends AkaDaoSupport {

	public abstract  int insert(InterLogNotifyMoreBean notify) throws Exception ;
	public abstract  void update(InterLogNotifyMoreBean notify) throws Exception;
	public abstract InterLogNotifyMoreBean getOne(String requestNo,
												  String responseType,
												  String serviceName) throws Exception;


}
