package com.github.ulwx.aka.frame.process;

import javax.servlet.http.HttpServletRequest;

import com.github.ulwx.aka.frame.protocol.res.BaseRes;

public abstract class Processor {

	public abstract BaseRes process(HttpServletRequest request,ProcessContext context)
				throws Exception;
}
