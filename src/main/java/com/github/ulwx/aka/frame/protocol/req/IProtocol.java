package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.BaseRes;

public interface IProtocol {
	
	public  BaseRes genBean() throws Exception;

	public  BaseRes getTestBean() throws Exception;

	public String getInterType();
}
