
package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.BaseResBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.protocol.utils.UlFrameSignatures;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.MD5;
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
			AkaFrameProperties akaFrameProperties= BeanGet.getBean(AkaFrameProperties.class,request);
			BaseRes javaBean = null;
			String namespaceKey = (String) context.getFromGenArgs(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE);
			Boolean signEnable = akaFrameProperties.getRequest().getSign().getEnable();
			if(signEnable!=null && !signEnable){
				return null;
			}
			List<String> signVerifyProcessorNo= akaFrameProperties.getRequest().getSign().getExcludeProtocol();
			log.debug("signVerifyProcessorNo=" + signVerifyProcessorNo);
			if (!signVerifyProcessorNo.isEmpty()) {
				String str = (String) context.getFromGenArgs(UiFrameConstants.PROTOCOL_REQ_PARM_BN);
				if (signVerifyProcessorNo.contains(str)) {
					return null;

				}
			}
			String ndjh = context.getStringFromReqArgs(UiFrameConstants.PROTOCOL_REQ_NDJH);

			javaBean = validateSign(namespaceKey, context.getReqArgs(), ndjh, request);
			return javaBean;

		} catch (Exception e) {
			log.error("", e);
			return BaseResBean.ERROR("参数有错误！");
		}

	}

	public static BaseRes validateSign(String propertyKey,Map<String, String[]> parmMap
			,String ndjh, HttpServletRequest request){
		BaseRes javaBean = null;
		AkaFrameProperties akaFrameProperties= BeanGet.getBean(AkaFrameProperties.class,request);
		String  NDJH=akaFrameProperties.getRequest().getDebug().getNdjh();

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
			
			String signKey=akaFrameProperties.getRequest().getSign().getRequestSignKey();
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
		// serverSign=689681f58c914628f2b7f912dbca0e47;sign=36B39FE76B9677F2869EBA68C9FDCD6B

		String str = "addrId=0&address={\"name\":\"大石小学\",\"parentId\":1948}&appName=沐春芽-家长端&appVer=1.0&isHomeAddr=0&linkman=李大哥&model=iPhone&opType=1&phoneNumber=18022408390&postalCode=515151&sysPlatform=iOS&sysVersion=9.0.2&timestamp=1504515795&udid=D10B8107-8040-4345-96D3-7CEEBB26C639&userType=1&ver=1&key=xygedgeerxs@345%4";

		System.out.println(MD5.MD5generator(str, "utf-8"));

	}

}
