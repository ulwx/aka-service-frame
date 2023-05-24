package com.github.ulwx.aka.frame;

import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.WebMvcActiionContextConst;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
public class UIFrameAppConfig implements InitializingBean  {
	private AkaFrameProperties akaFrameProperties;
	private BeanGet beanGet;
	private Map<String, AkaFrameProperties.ProtocolProperties> namespaceToProtocolProperties=new HashMap<>();
	private Map<String, AkaFrameProperties.Handler> requestHandlerMap=new TreeMap<>();
	public BeanGet getBeanGet() {
		return beanGet;
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
		akaFrameProperties.getProtocols().stream().forEach(p-> {
			namespaceToProtocolProperties.put(p.getNamesapce(),p);
		});
		requestHandlerMap=akaFrameProperties.getHandlers();


	}

	private static Logger log = Logger.getLogger(UIFrameAppConfig.class);

	public AkaFrameProperties.ProtocolProperties getProtocolInfo(String namespace){
		return namespaceToProtocolProperties.get(namespace);
	}

	public AkaFrameProperties.Handler getRequestHander(String namespace){
		return this.requestHandlerMap.get(getProtocolInfo(namespace).getHandler());
	}

	public String getNameSpace(){
		ActionMethodInfo actionMethodInfo= WebMvcActiionContextConst.getActionMethodInfo();
		return actionMethodInfo.getNamespace().getName();
	}

	public AkaFrameProperties.Storage getStorage(){
		return getRequestHander(getNameSpace()).getStorage();
	}
	public AkaFrameProperties.Handler getRequestHander(){
		return this.requestHandlerMap.get(getProtocolInfo(this.getNameSpace()).getHandler());
	}

	public static void main(String[] args) throws Exception {

	}

}
