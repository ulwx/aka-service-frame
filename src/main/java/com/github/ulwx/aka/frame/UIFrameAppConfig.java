package com.github.ulwx.aka.frame;

import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UIFrameAppConfig implements InitializingBean  {
	private AkaFrameProperties akaFrameProperties;
	private BeanGet beanGet;
	private Map<String,List<Protocol>> namespaceToProtocols=new TreeMap<>();
	private Map<String, AkaFrameProperties.ProtocolProperties> namespaceToProtocolProperties=new HashMap<>();
	private Map<String, AkaFrameProperties.RequestHandler> requestHandlerMap=new TreeMap<>();
	public BeanGet getBeanGet() {
		return beanGet;
	}

	public Map<String, List<Protocol>> getNamespaceToProtocols() {
		return namespaceToProtocols;
	}

	public void setNamespaceToProtocols(Map<String, List<Protocol>> namespaceToProtocols) {
		this.namespaceToProtocols = namespaceToProtocols;
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
		String globalErrClass=akaFrameProperties.getGlobalErrorClass();
		errorClassMap.put("global", Class.forName(globalErrClass));
		akaFrameProperties.getProtocols().stream().forEach(p-> {
					try {
						String errorClassName=StringUtils.trim(p.getErrorClass());
						if(!errorClassName.isEmpty()) {
							errorClassMap.put(p.getNamesapce(), Class.forName(p.getErrorClass()));
						}
						namespaceToProtocolProperties.put(p.getNamesapce(),p);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}});
		requestHandlerMap=akaFrameProperties.getHandlers();
		Map<String, Protocol> map=beanGet.beans(Protocol.class);
		for(String key:map.keySet()){
			Protocol protocol=map.get(key);
			AkaFrameProperties.ProtocolProperties protocolInfo=getProtocolInfo(protocol.getClass());
			List<Protocol> list=namespaceToProtocols.get(protocolInfo.getNamesapce());
			if(list==null){
				list=new ArrayList<>();
				namespaceToProtocols.put(protocolInfo.getNamesapce(),list);
			}
			list.add(protocol);

		}

	}

	private static Logger log = Logger.getLogger(UIFrameAppConfig.class);

	public AkaFrameProperties.ProtocolProperties getProtocolInfo(String namespace){
		return namespaceToProtocolProperties.get(namespace);
	}
	public AkaFrameProperties.ProtocolProperties getProtocolInfo(Class reqClass){
		List<AkaFrameProperties.ProtocolProperties> list=akaFrameProperties.getProtocols();
		//handlerMap=akaFrameProperties.getHandlers();
		for(int i=0; i<list.size(); i++){
			AkaFrameProperties.ProtocolProperties protocol=list.get(i);
			String clssName = reqClass.getName();
			if(clssName.startsWith(protocol.getPackageName())) {
				return protocol;
			}
		}

		return null;
	}
	public AkaFrameProperties.RequestHandler getRequestHander(String namespace){
		return this.requestHandlerMap.get(getProtocolInfo(namespace).getHandler());
	}

	public String getNameSpace(){
		return ReqContext.getRequestUtis().getString(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE);
	}

	public AkaFrameProperties.Storage getStorage(){
		return getRequestHander(this.getNameSpace()).getStorage();
	}
	public AkaFrameProperties.RequestHandler getRequestHander(){
		return this.requestHandlerMap.get(getProtocolInfo(this.getNameSpace()).getHandler());
	}

	public static void main(String[] args) throws Exception {

	}

}
