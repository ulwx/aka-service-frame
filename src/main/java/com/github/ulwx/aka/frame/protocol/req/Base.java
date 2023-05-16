package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.ulwx.tool.FileUtils;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.RequestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 协议基类
 * 
 */
public  class Base {
	/**
	 * 日志
	 */
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 请求注入
	 */
	protected RequestUtils request;

	/**
	 * 获取请求注入
	 * 
	 * @return
	 */
	public RequestUtils getRequest() {
		return request;
	}

	public String getGateWayIP() {
		return this.getRequest().getString(UiFrameConstants.PROTOCOL_REQ_REMOTE_IP);
	}

	public String getHttpRealPath() {
		 return FileUtils.getDirectoryPath(this.getHttpRequest().getServletContext().getRealPath(""));
	}
	public String getHttpRequestContext() {
		return this.getHttpRequest().getContextPath();
	}
	public String getHttpURLRoot() {

		String url = this.getHttpRequest().getScheme() + "://" + this.getHttpRequest().getServerName() + ":" + this.getHttpRequest().getServerPort();
		return url;
	}
	/**
	 * 获取项目全路径名
	 * @return 
	 */
	public String getWebRoot(){
		return this.getHttpURLRoot()+this.getHttpRequestContext();
	}
	
	
	public HttpServletResponse getHttpResponse() {
		HttpServletResponse response = (HttpServletResponse)this.getRequest().getObject(UiFrameConstants.PROTOCOL_REQ_RESPONSE);
		return response;
	}

	public HttpServletRequest getHttpRequest() {

		HttpServletRequest request = (HttpServletRequest)this.getRequest().getObject(UiFrameConstants.PROTOCOL_REQ_REQUEST);
		return request;

	}


	/**
	 * 设置请求注入
	 * 
	 * @param request
	 */
	public void setRequest(RequestUtils request) {
		this.request = request;
	}
	
	
	public <T extends Base> T toBean(Class<T> targetClass,Class parentClass){
		Base op=ObjectUtils.fromBeanWithPublicFieldToBean(targetClass, this, parentClass);
		op.setRequest(this.getRequest());
		
		return (T)op;
	}

	/**
	 * 业务类型，子类可根据业务情况覆盖
	 * @return
	 */
	public String getServiceName(){
		return this.getClass().getSimpleName();
	}

}
