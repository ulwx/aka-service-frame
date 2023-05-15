package com.github.ulwx.aka.frame.servlet.support;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.webmvc.BeanGet;

import javax.servlet.http.HttpServletRequest;

public abstract class NotifyReqParser {

	public abstract NotfyReqParm parseRequest(HttpServletRequest request,ReqCodeParm reqCodeParm)  throws Exception;
	/**
	 *
	 * @param namespaceKey 请求URL的命名空间，对应aka-service-frame.properties里的protocol_namespace属性
	 * @param parseCode 解析编码，对应aka-service-frame.properties里的notify.req.parser.[parseCode]配置，用于指定请求的解析插件
	 * @return
	 */
	public static NotifyReqParser getInstance(String namespaceKey,String parseCode) {

		NotifyReqParser parser=null;
		AkaFrameProperties akaFrameProperties = BeanGet.getBean(AkaFrameProperties.class);
		String className = akaFrameProperties.getNotifyRequest().getParsers().get(parseCode);
		try {
			parser= (NotifyReqParser) Class.forName(className).getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return parser;
	}

}
