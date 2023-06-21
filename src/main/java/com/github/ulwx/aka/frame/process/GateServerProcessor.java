package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UiFrameConstants;
import com.github.ulwx.aka.frame.utils.NetTools;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("com.github.ulwx.aka.frame.process.GateServerProcessor")
@Order(2)
public class GateServerProcessor extends ActionSupport implements FrameProcess{
	private static Logger log = Logger.getLogger(GateServerProcessor.class);
	@Override
	public String process(HttpServletRequest request,
								ActionMethodInfo actionMethodInfo,
								RequestUtils context) {
		// TODO Auto-generated method stub
		String gateKey ="";
		String gateIP = "";
		String phoneFromGateWay="";
		
    	gateKey = StringUtils.trim(request.getHeader("Via"));
		gateIP = NetTools.getRemoteAddr(request);
		phoneFromGateWay=getPhoneFromGateWay(request);
		if(StringUtils.containsIgnoreCase(gateKey, "WAP")){
			context.setBoolean(UiFrameConstants.PROTOCOL_REQ_GATEWAY_IS_CMWAP, true );
		}else{
			context.setBoolean(UiFrameConstants.PROTOCOL_REQ_GATEWAY_IS_CMWAP, false );
		}
		context.setString(UiFrameConstants.PROTOCOL_REQ_GATEWAY_PHONE,  phoneFromGateWay );
		context.setString(UiFrameConstants.PROTOCOL_REQ_REMOTE_IP, gateIP );
		// 网关关键字
		context.setString(UiFrameConstants.PROTOCOL_REQ_GATEWAY_KEY, gateKey );
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
