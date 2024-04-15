package com.github.ulwx.aka.frame;

import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.WebMvcActiionContextConst;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
@RefreshScope
@Component("com.github.ulwx.aka.frame.UIFrameAppConfig")
public class UIFrameAppConfig implements InitializingBean  {
	private AkaFrameProperties akaFrameProperties;
	private BeanGet beanGet;
	private Map<String, AkaFrameProperties.ProtocolProperties> namespaceToProtocolProperties=new HashMap<>();
	private Map<String, AkaFrameProperties.Handler> requestHandlerMap=new TreeMap<>();
	public BeanGet getBeanGet() {
		return beanGet;
	}

	public UIFrameAppConfig(){
		int i=0;
	}
	@Autowired
	public void setBeanGet(BeanGet beanGet) {
		this.beanGet = beanGet;
	}

	public static Map<String ,Class> errorClassMap= new HashMap<>();

	public AkaFrameProperties getAkaFrameProperties() {
		return akaFrameProperties;
	}
	@Autowired
	public void setAkaFrameProperties(AkaFrameProperties akaFrameProperties) {
		this.akaFrameProperties = akaFrameProperties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, AkaFrameProperties.ProtocolProperties> map= akaFrameProperties.getProtocols();
		for(String key: map.keySet()){
			AkaFrameProperties.ProtocolProperties protocolProperties=map.get(key);
			protocolProperties.setNamesapce(key);;
		}
		namespaceToProtocolProperties.putAll(akaFrameProperties.getProtocols());
		requestHandlerMap=akaFrameProperties.getHandlers();


	}

	private static Logger log = Logger.getLogger(UIFrameAppConfig.class);

	public Set<String> getAllNameSpaces(){
		return namespaceToProtocolProperties.keySet();
	}
	public AkaFrameProperties.ProtocolProperties getProtocolInfo(String namespace){
		return namespaceToProtocolProperties.get(namespace);
	}

	public AkaFrameProperties.Handler getRequestHander(String namespace){
		AkaFrameProperties.ProtocolProperties protocolProperties=getProtocolInfo(namespace);
		if(protocolProperties==null) {
			return null;
		}
		String handlerName = protocolProperties.getHandler();
		return this.requestHandlerMap.get(handlerName);

	}

	public String getCurNameSpace(){
		ActionMethodInfo actionMethodInfo= WebMvcActiionContextConst.getActionMethodInfo();
		return actionMethodInfo.getNamespace().getName();
	}

	public AkaFrameProperties.Storage getCurStorage(){
		return getRequestHander(getCurNameSpace()).getStorage();
	}
	public AkaFrameProperties.Handler getCurRequestHander(){
		AkaFrameProperties.ProtocolProperties protocolProperties=getProtocolInfo(this.getCurNameSpace());
		if(protocolProperties==null) {
			return null;
		}
		return this.requestHandlerMap.get( protocolProperties.getHandler());
	}

	public static void main(String[] args) throws Exception {

	}

}
