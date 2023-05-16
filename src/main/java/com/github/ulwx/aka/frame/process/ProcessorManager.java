package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public  class ProcessorManager {

	private static Logger log = Logger.getLogger(ProcessorManager.class);
	//新建一个Processor，必须在此处配置
	
	public static ProcessorManager instance=new ProcessorManager();
	

	public  BaseRes process(HttpServletRequest request,ProcessContext context)throws Exception
	{
		UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
		List<String> processors=akaFrameProperties.getRequestHander().getProcessors();
		for(int i=0; i<processors.size(); i++){
			String className=processors.get(i).trim();
			Processor p=(Processor)Class.forName(className).newInstance();
			BaseRes ret=p.process(request, context);
			if(ret==null){
				continue;
			}else{
				return ret;
			}
		}
		return null;
		
	}
}
