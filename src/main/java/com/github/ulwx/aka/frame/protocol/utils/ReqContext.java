package com.github.ulwx.aka.frame.protocol.utils;

import com.github.ulwx.aka.frame.protocol.req.Base;
import com.ulwx.tool.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReqContext implements Serializable {

	private static final long serialVersionUID = -2348853553489025507L;
	private volatile Map<String, Object> dataMap=new ConcurrentHashMap<String,Object>();
	
	static volatile ThreadLocal<ReqContext> actionContext = new ThreadLocal<ReqContext>(){
        @Override
        protected ReqContext initialValue() {
            return  new ReqContext();
        }
	};

	public static final String REQUEST_OBJ = "FRAME_REQUEST_OBJ";
	public static final String REQUEST_UTIS="REQUEST_UTIS";


	private ReqContext() {
	}

	public static void clear() {
		actionContext.remove();
	}


	public Object get(String key) {
		return this.dataMap.get(key);
	}
	public void set(String key,Object value) {
		this.dataMap.put(key, value);
	}

	public static void setRequestUtis(RequestUtils requestUtils){
		actionContext.get().dataMap.put(REQUEST_UTIS,requestUtils);
	}
	public static RequestUtils getRequestUtis(){
		return (RequestUtils)actionContext.get().dataMap.get(REQUEST_UTIS);
	}
	public static HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest) getRequestUtis().getObject(UiFrameConstants.PROTOCOL_REQ_REQUEST);
	}

	public static Base getRequestBean() {
		return (Base) getRequestUtis().getObject(UiFrameConstants.PROTOCOL_REQ_OBJ);
	}


}
