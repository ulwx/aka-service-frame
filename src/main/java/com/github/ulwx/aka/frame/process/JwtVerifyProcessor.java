package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.BaseResBean;
import com.github.ulwx.aka.frame.protocol.utils.IError;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.utils.JwtHelper;
import com.github.ulwx.aka.frame.utils.JwtInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.utils.WebMvcCbConstants;
import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * jwt 验证
 * 
 * @author yong
 *
 */
public class JwtVerifyProcessor extends Processor {
	private static Logger log = Logger.getLogger(JwtVerifyProcessor.class);

	@Override
	public BaseRes process(HttpServletRequest request, ProcessContext context) throws Exception {

		String namespace = context.getString(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE);
		BaseRes javaBean = null;
		UIFrameAppConfig akaFrameProperties= BeanGet.getBean(UIFrameAppConfig.class,request);
		HttpSession session = request.getSession();
		Object userInfo = session.getAttribute(WebMvcCbConstants.SessionKey.USER);
		if (userInfo != null) {// 说明是接口登陆后
			return null;
		}
		if(akaFrameProperties.getRequestHander().getDebug()!=null) {
			String ndjh = context.getString("ndjh");
			String NDJH = akaFrameProperties.getRequestHander().getDebug().getNdjh();
			if ((StringUtils.hasText(NDJH) && NDJH.equals(ndjh))) {// 忽略验证
				log.debug("后门：[" + NDJH + "," + ndjh + "]jwt验证忽略");
				// 看是否在白名单里
				List<String> whitelist = akaFrameProperties.getRequestHander().getDebug().getIpAccessWhitelist();
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
		Boolean isJwtValidate = akaFrameProperties.getRequestHander().getJwtVerify().getEnable();
		if(isJwtValidate!=null && !isJwtValidate) {
			return null;
		}
		try {
			log.debug("jwt验证");
			List<String> excludes=akaFrameProperties.getRequestHander().getJwtVerify().getExcludeProtocol();
			String module = context.getString(UiFrameConstants.PROTOCOL_REQ_PARM_MOD_NAME);//
			String protocol =  context.getString(UiFrameConstants.PROTOCOL_REQ_PARM_BN); // context.getStringInReqArgs("protocol");
			String compareStr = module + "." + protocol;
			if (StringUtils.hasText(protocol)) {

				for (int i = 0; i < excludes.size(); i++) {
					String str = excludes.get(i);
					if (str.charAt(str.length() - 1) == '*') {
						str = StringUtils.trimTailString(str, "*");
						if (!str.isEmpty() && compareStr.startsWith(str)) {
							return null;
						}
					} else {
						if (compareStr.equals(excludes.get(i))) {
							return null;
						}
					}
				}

			} else {
				return BaseResBean.ERROR("参数有错误！[协议号为空]");
			}

			String authHeader = request.getHeader("Authorization");
			if (StringUtils.isEmpty(authHeader)) {
				authHeader = context.getString("Authorization");
			}
			log.debug("Authorization=" + authHeader);
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				javaBean = BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "认证信息为空");
				return javaBean;
			}

			// 取得token
			String jwt = authHeader.substring(7);
			if (StringUtils.isEmpty(jwt)) {
				return BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "参数有错误！[Authorization验证出错]");
			}
			JwtInfo jwtInfo = JwtHelper.parseJWT(jwt,
					akaFrameProperties.getRequestHander().getJwtVerify().getSecret()
			);

			String deviceid = context.getString(JwtInfo.DEVICE_ID);

			if (jwtInfo.getDeviceID().equals(deviceid)) {
				context.setString(UiFrameConstants.PROTOCOL_REQ_PARM_USER, jwtInfo.getUser());
				context.setString(UiFrameConstants.PROTOCOL_REQ_PARM_UDID, jwtInfo.getDeviceID());
				context.setObject(UiFrameConstants.PROTOCOL_REQ_JWTINFO, jwtInfo);
				String pluginClass =
						akaFrameProperties.getRequestHander().getJwtVerify().getVerifyPluginClass();
				if (StringUtils.hasText(pluginClass)) {
					JwtVerifyPlugin plugin = (JwtVerifyPlugin) Class.forName(pluginClass).newInstance();
					JwtVerifyPlugin.VerifyResult verifyResult = plugin.verify(jwtInfo);
					if (verifyResult != null && !verifyResult.isSuccess()) {
						return BaseResBean.ERROR(verifyResult.getErrorCode(), verifyResult.getMessage());
					}
				}
				return null;
			} else {
				return BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "验证失败！");
			}

		} catch (Exception e) {
			log.error("", e);
			return BaseResBean.ERROR(IError.JWT_VERIFY_FAIL, "验证出错[" + e + "]");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "aa_bb_cc_ _dd";
		System.out.println(ArrayUtils.toJsonString(s.split("_")));

	}

}
