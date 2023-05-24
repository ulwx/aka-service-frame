package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.UiFrameConstants;
import com.github.ulwx.aka.frame.protocol.utils.IError;
import com.github.ulwx.aka.frame.utils.JwtHelper;
import com.github.ulwx.aka.frame.utils.JwtInfo;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * jwt 验证
 * 
 * @author yong
 *
 */
@Component
@Order(3)
public class JwtVerifyProcessor extends ActionSupport implements FrameProcess{
	private static Logger log = Logger.getLogger(JwtVerifyProcessor.class);
	private static final AntPathMatcher pathMatcher = new AntPathMatcher();
	@Override
	public String process(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context) {
		boolean jsonResponse=actionMethodInfo.getJSONResponse();
		String reponseContentType=actionMethodInfo.getAnnoClassMethodInfo().getResponseContentType();
		//if(actionMethodInfo.getAnnoClassMethodInfo().getResponseContentType().contains("json"))
		String namespace = actionMethodInfo.getNamespace().getName();
		Object javaBean = null;
		UIFrameAppConfig uIFrameAppConfig= BeanGet.getBean(UIFrameAppConfig.class,request);
		HttpSession session = request.getSession();

		Boolean isJwtValidate = uIFrameAppConfig.getRequestHander(namespace).getJwtVerify().getEnable();
		if(isJwtValidate!=null && !isJwtValidate) {
			return null;
		}
		if(uIFrameAppConfig.getRequestHander(namespace).getDebug()!=null) {
			String ndjh = context.getString("ndjh");
			String NDJH = uIFrameAppConfig.getRequestHander(namespace).getDebug().getNdjh();
			if ((StringUtils.hasText(NDJH) && NDJH.equals(ndjh))) {// 忽略验证
				log.debug("后门：[" + NDJH + "," + ndjh + "]jwt验证忽略");
				// 看是否在白名单里
				List<String> whitelist = uIFrameAppConfig.getRequestHander(namespace).getDebug().getIpAccessWhitelist();
				if (!whitelist.isEmpty()) {
					String remoteIp = context.getString(UiFrameConstants.PROTOCOL_REQ_REMOTE_IP);
					log.debug("remoteIp=" + remoteIp);
					for (int i = 0; i < whitelist.size(); i++) {
						String compareIp = whitelist.get(i).replaceAll("\\.\\*", "");
						if (remoteIp.startsWith(compareIp)) {
							return null;
						}
					}
				} else {
					return null;
				}

			}
		}

		try {
			log.debug("jwt验证");
			List<String> excludes=uIFrameAppConfig.getRequestHander(namespace).getJwtVerify().getExcludeProtocol();
			String protocol =  actionMethodInfo.getActionLogicName(); // context.getStringInReqArgs("protocol");
			String compareStr = actionMethodInfo.getLogicActionMethodName();
			if (StringUtils.hasText(protocol)) {
				for (int i = 0; i < excludes.size(); i++) {
					String str = excludes.get(i);
					if (pathMatcher.match(str.replace("_", "-"),
							compareStr.replace("_", "-"))) {
						return null;
					}

				}

			} else {
				if(jsonResponse){
					return  this.JsonViewError("参数有错误！[协议号为空]");
				}else{
					return this.errorView(0,"参数有错误！[协议号为空]","");
				}
			}

			String authHeader = request.getHeader("Authorization");
			if (StringUtils.isEmpty(authHeader)) {
				authHeader = context.getString("Authorization");
			}
			if (StringUtils.isEmpty(authHeader)) {
				Cookie[] cookies =cookies = request.getCookies();
				if( cookies != null ){
					for (int i = 0; i < cookies.length; i++){
						Cookie cookie = cookies[i];
						if((cookie.getName( ).equals("Authorization"))){
							authHeader=cookie.getValue();
						}

					}
				}
			}
			log.debug("Authorization=" + authHeader);
			authHeader=StringUtils.trim(authHeader);
			if(authHeader.isEmpty()){
				return this.getResult(jsonResponse,IError.JWT_VERIFY_FAIL,"认证信息为空");
			}
			// 取得token""
			String jwt="";
			if(StringUtils.startsWithIgnoreCase(authHeader,"Bearer ")) {
				jwt = authHeader.substring(7);
			}else{
				jwt=authHeader;
			}
			if (StringUtils.isEmpty(jwt)) {
				return this.getResult(jsonResponse,IError.JWT_VERIFY_FAIL,"参数有错误！[Authorization验证出错]");
			}
			JwtInfo jwtInfo = JwtHelper.parseJWT(jwt,
					uIFrameAppConfig.getRequestHander(namespace).getJwtVerify().getSecret()
			);

			String deviceid = context.getString(JwtInfo.DEVICE_ID);

			if (jwtInfo.getDeviceID().equals(deviceid)) {
				context.setObject(UiFrameConstants.PROTOCOL_REQ_JWTINFO, jwtInfo);
				String pluginClass =
						uIFrameAppConfig.getRequestHander(namespace).getJwtVerify().getVerifyPluginClass();
				pluginClass=StringUtils.trim(pluginClass);
				if (StringUtils.hasText(pluginClass)) {
					if(pluginClass.equalsIgnoreCase("NONE")){
						return null;
					}
					JwtVerifyPlugin plugin = (JwtVerifyPlugin) Class.forName(pluginClass).newInstance();
					JwtVerifyPlugin.VerifyResult verifyResult = plugin.verify(jwtInfo);
					if (verifyResult != null && !verifyResult.isSuccess()) {
						return this.getResult(jsonResponse,verifyResult.getErrorCode(),verifyResult.getMessage());
					}
				}
				return null;
			} else {
				return this.getResult(jsonResponse,IError.JWT_VERIFY_FAIL,"验证失败！");
				//return BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "验证失败！");
			}

		} catch (Exception e) {
			log.error("", e);
			//return BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "验证出错[" + e + "]");
			return this.getResult(jsonResponse,IError.JWT_VERIFY_FAIL,"验证出错[" + e + "]");
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
