package com.github.ulwx.aka.frame.utils;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
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
						errorClassMap.put(p.getNamesapce(),Class.forName(p.getErrorClass()));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}});
		Map<String, Protocol> map=beanGet.beans(Protocol.class);
		for(String key:map.keySet()){
			Protocol protocol=map.get(key);
			AkaFrameProperties.Protocol protocolInfo=getProtocolInfo(protocol.getClass());
			List<Protocol> list=namespaceToProtocols.get(protocolInfo.getNamesapce());
			if(list==null){
				list=new ArrayList<>();
				namespaceToProtocols.put(protocolInfo.getNamesapce(),list);
			}
			list.add(protocol);

		}

	}

	private static Logger log = Logger.getLogger(UIFrameAppConfig.class);

	public AkaFrameProperties.Protocol getProtocolInfo(String namespace){
		List<AkaFrameProperties.Protocol> list=akaFrameProperties.getProtocols();
		for(int i=0; i<list.size(); i++){
			AkaFrameProperties.Protocol protocol=list.get(i);
			if(namespace.startsWith(protocol.getNamesapce())) {
				return protocol;
			}
		}

		return null;
	}
	public  AkaFrameProperties.Protocol getProtocolInfo(Class reqClass){
		List<AkaFrameProperties.Protocol> list=akaFrameProperties.getProtocols();
		for(int i=0; i<list.size(); i++){
			AkaFrameProperties.Protocol protocol=list.get(i);
			String clssName = reqClass.getName();
			if(clssName.startsWith(protocol.getPackageName())) {
				return protocol;
			}
		}

		return null;
	}

	public static void main(String[] args) throws Exception {

	}

}
