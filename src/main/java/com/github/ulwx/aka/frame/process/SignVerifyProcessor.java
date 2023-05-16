
package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.BaseResBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.protocol.utils.UlFrameSignatures;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class SignVerifyProcessor extends Processor {
	private static Logger log = Logger.getLogger(SignVerifyProcessor.class);

	@Override
	public BaseRes process(HttpServletRequest request, ProcessContext context) throws Exception {

		try {
			UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
			BaseRes javaBean = null;
			String namespaceKey = context.getString(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE);
			Boolean signEnable = akaFrameProperties.getRequestHander(namespaceKey).getSign().getEnable();
			if(signEnable!=null && !signEnable){
				return null;
			}
			List<String> signVerifyProcessorNo= akaFrameProperties.getRequestHander().getSign().getExcludeProtocol();
			log.debug("signVerifyProcessorNo=" + signVerifyProcessorNo);
			if (!signVerifyProcessorNo.isEmpty()) {
				String str =context.getString(UiFrameConstants.PROTOCOL_REQ_PARM_BN);
				if (signVerifyProcessorNo.contains(str)) {
					return null;
				}
			}
			String ndjh = context.getString(UiFrameConstants.PROTOCOL_REQ_NDJH);
			javaBean = validateSign(namespaceKey, request.getParameterMap(), ndjh, request);
			return javaBean;

		} catch (Exception e) {
			log.error("", e);
			return BaseResBean.ERROR("参数有错误！");
		}

	}

	public static BaseRes validateSign(String propertyKey,Map<String, String[]> parmMap
			,String ndjh, HttpServletRequest request){
		BaseRes javaBean = null;
		UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
		String  NDJH=akaFrameProperties.getRequestHander().getDebug().getNdjh();

		if(StringUtils.hasText(NDJH) && NDJH.equals(ndjh)){//忽略验证
			log.debug("sign="+ndjh+",忽略验证！");
			return null;
		}
		if (parmMap.isEmpty()) {
			return BaseResBean.ERROR("查询参数非法[02]");
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
				return BaseResBean.ERROR("参数验证失败[sign为空]");
			}
			
			String signKey=akaFrameProperties.getRequestHander().getSign().getRequestSignKey();
			String serverSign = UlFrameSignatures.signature(signKey,signMessage);
			log.debug("serverSign="+serverSign+";sign="+sign);
			log.debug("rquest params["+signMessage+"&sign="+sign+"]");
			if (!serverSign.equalsIgnoreCase(sign)) {
				javaBean = BaseResBean.ERROR("参数非法【签名验证失败】");
			} else {
				javaBean = null;
			}
		
		}
		return javaBean;
	
	}

	public static void main(String[] args) {

	}

}
