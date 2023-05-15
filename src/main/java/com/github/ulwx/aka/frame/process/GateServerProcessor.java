package com.github.ulwx.aka.frame.process;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.utils.NetTools;


public class GateServerProcessor extends Processor {
	private static Logger log = Logger.getLogger(GateServerProcessor.class);
	@Override
	public BaseRes process(HttpServletRequest request,ProcessContext context) {
		// TODO Auto-generated method stub
		String gateKey ="";
		String gateIP = "";
		String phoneFromGateWay="";
		
    	gateKey = StringUtils.trim(request.getHeader("Via"));
		gateIP = NetTools.getRemoteAddr(request);
		phoneFromGateWay=getPhoneFromGateWay(request);

		if(StringUtils.containsIgnoreCase(gateKey, "WAP")){
			context.putToGenArgs(UiFrameConstants.PROTOCOL_REQ_GATEWAY_IS_CMWAP, true );
		}else{
			context.putToGenArgs(UiFrameConstants.PROTOCOL_REQ_GATEWAY_IS_CMWAP, false );
		}
		context.putToGenArgs(UiFrameConstants.PROTOCOL_REQ_GATEWAY_PHONE,  phoneFromGateWay );

		context.putToGenArgs(UiFrameConstants.PROTOCOL_REQ_REMOTE_IP, gateIP );
		// 网关关键字
		context.putToGenArgs(UiFrameConstants.PROTOCOL_REQ_GATEWAY_KEY, gateKey );
		//log.debug("---"+CollectionUtils.toString(parmMap));
		
		return null;

	}

	public static String getPhoneFromGateWay(HttpServletRequest request){
		String phoneFromGateWay=StringUtils.trim(request.getHeader("x-up-calling-line-id"));
		if(StringUtils.isEmpty(phoneFromGateWay)){
			phoneFromGateWay=StringUtils.trim(request.getHeader("X-Up-Calling-Line-ID"));
		}
		return phoneFromGateWay;
	}
	
	
	

}
