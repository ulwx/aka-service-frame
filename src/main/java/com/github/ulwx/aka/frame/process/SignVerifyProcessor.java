
package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.UiFrameConstants;
import com.github.ulwx.aka.frame.UlFrameSignatures;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Component
@Order(4)
public class SignVerifyProcessor extends ActionSupport implements FrameProcess{
	private static Logger log = Logger.getLogger(SignVerifyProcessor.class);
	private static final AntPathMatcher pathMatcher = new AntPathMatcher();
	@Override
	public String process(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context) {

		Boolean jsonResponse=actionMethodInfo.getJSONResponse();
		try {
			UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
			String namespace = actionMethodInfo.getNamespace().getName();
			Boolean signEnable = akaFrameProperties.getRequestHander(namespace).getSignVerify().getEnable();
			if(signEnable!=null && !signEnable){
				return null;
			}
			List<String> signVerifyProcessorNo= akaFrameProperties.getRequestHander(namespace).getSignVerify().getExcludeProtocol();
			log.debug("signVerifyProcessorNo=" + signVerifyProcessorNo);
			if (!signVerifyProcessorNo.isEmpty()) {
				String str =actionMethodInfo.getLogicActionMethodName();//context.getString(UiFrameConstants.PROTOCOL_REQ_PARM_BN);
				for(int i=0; i<signVerifyProcessorNo.size();i++) {
					if (pathMatcher.match(signVerifyProcessorNo.get(i).replace("_", "-"),
							str.replace("_", "-"))) {
						return null;
					}
				}
			}
			String ndjh = context.getString(UiFrameConstants.PROTOCOL_REQ_NDJH);
			return  validateSign(namespace, request.getParameterMap(), ndjh, request,jsonResponse);

		} catch (Exception e) {
			log.error("", e);
			return this.getResult(jsonResponse,0,e+"");
		}

	}

	public  String validateSign(String namespace,Map<String, String[]> parmMap
			,String ndjh, HttpServletRequest request,Boolean jsonResponse){

		UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
		String  NDJH=akaFrameProperties.getRequestHander(namespace).getDebug().getNdjh();

		if(StringUtils.hasText(NDJH) && NDJH.equals(ndjh)){//忽略验证
			log.debug("sign="+ndjh+",忽略验证！");
			return null;
		}
		if (parmMap.isEmpty()) {
			return this.getResult(jsonResponse,0,"查询参数非法[02]");
		} else {
			String signMessage="";
			String sign="";
			for(String key:parmMap.keySet()){
				if(key.equals("sign")){
					String[] signs=parmMap.get("sign");
					if(ArrayUtils.isNotEmpty(signs)){
						sign=signs[0];
					}
					continue;
				}
				String[] vals=parmMap.get(key);
				String val="";
				if(ArrayUtils.isNotEmpty(vals)){
					val=vals[0];
				}
				String str=key+"="+val;
				signMessage=signMessage+"&"+str;
				
			}
			signMessage=StringUtils.trimLeadingString(signMessage, "&");
			log.debug("signMessage["+signMessage+"]");
			
			if(StringUtils.isEmpty(sign)){
				return this.getResult(jsonResponse,0,"参数验证失败[sign为空]");
			}
			
			String signKey=akaFrameProperties.getRequestHander(namespace).getSignVerify().getRequestSignKey();
			String serverSign = UlFrameSignatures.signature(signKey,signMessage);
			log.debug("serverSign="+serverSign+";sign="+sign);
			log.debug("rquest params["+signMessage+"&sign="+sign+"]");
			if (!serverSign.equalsIgnoreCase(sign)) {
				return this.getResult(jsonResponse, 0,"参数非法【签名验证失败】");
			} else {
				return null;
			}
		
		}

	
	}


	private String	getResult(Boolean jsonResponse,int errorCode,String msg){
		if(jsonResponse){
			return  this.JsonViewError(errorCode, msg);
		}else{
			return this.errorView(errorCode, msg,"");
		}
	}
}
