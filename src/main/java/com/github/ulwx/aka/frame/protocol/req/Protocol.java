package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.annotation.InterfaceTest;
import com.github.ulwx.aka.frame.annotation.Validate;
import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
import com.github.ulwx.aka.frame.utils.UIFrameAppConfig;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.exception.ServiceException;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public abstract class Protocol extends Base{

	protected  Logger logger = Logger.getLogger(this.getClass());
	
	@InterfaceTest(value = "",display =true)
	@Comment(cn = "请求流水号")
	@Validate
	public String requestid;
	
	@InterfaceTest(value = "0",display =true)
	@Comment(cn = "用户id，有些接口不存在用户id，比如登录接口")
	public String userid;
	
	@InterfaceTest(value = "",display =true)
	@Comment(cn = "设备Id，根据场景不同含义不同，有可能是赋值为用户id")
	public String udid;
	
	@Comment(cn = "来源标识, 格式为： <产品标识_版本号>-<渠道标识>-<用户操作系统标识_版本号>-<设备序列号或设备id>-<设备型号>-<扩展信息>")
	@InterfaceTest(value = "pc_1.0-no-no-no-no-no",display =true)
	public String sourceId;
	
	@Comment(cn = "是否时测试，0：正式 1：测试")
	@InterfaceTest(value = "0",display =true)
	public Integer test=0;

	@Validate
	@InterfaceTest(display = false)
	@Comment(cn = "协议号")
	public String protocol;// 10001
	@Validate
	@InterfaceTest(value = "1.0.0",display = false)
	@Comment(cn = "协议版本号")
	public String ver; //协议版本号 1.0.0
	
	@Comment(cn = "远程IP")
	@InterfaceTest(value = "0.0.0.0",display =false)
	public String remoteIp;
	
	@InterfaceTest(display = false)
	@Comment(cn = "模块名")
	public String moduleName;//moduleName
	
	@InterfaceTest(display = false)
	@Comment(cn = "请求命名空间")
	public String namespace;
	
	@InterfaceTest(display = true)
	@Comment(cn = "签名")
	public String sign;

	@Validate
	@InterfaceTest(value = "",display =true)
	@Comment(cn = "时间戳")
	public Long timestamp;
	

	public static class Status{

		public static final Integer FAIL = 0;//失败

		public static final Integer SUCCESS = 1;// 成功
	}
	
	
	public String getSign() {
		return sign;
	}

	public String getVer() {
		return ver;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUserid() {
		return userid;
	}

	public String getUdid() {
		return udid;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	/**
	 * 业务逻辑处理
	 * 
	 * @return 如果返回为null，表示验证通过；如果为非null，表明验证不通过
	 * @throws Exception
	 */
	public abstract String validate() throws Exception;
	

	public <T extends Protocol> T cloneBaseTo(T dest) { 
		dest.namespace=this.namespace;
		dest.moduleName=this.moduleName;
		dest.protocol=dest.getClass().getSimpleName();
		dest.remoteIp=this.getRemoteIp();
		dest.requestid="";
		dest.ver=this.ver;
		dest.udid=this.udid;
		dest.sourceId=this.sourceId;
		dest.timestamp=this.timestamp;
		dest.userid=this.userid;
		return dest;
	}
	public <T extends Protocol> T cloneBaseFrom(Protocol from) {
		this.namespace=from.namespace;
		this.moduleName=from.moduleName;
		this.protocol=this.getClass().getSimpleName();
		this.remoteIp=from.getRemoteIp();
		this.requestid="";
		this.ver=from.ver;
		this.udid=from.udid;
		this.sourceId=from.sourceId;
		this.timestamp=from.timestamp;
		this.userid=from.userid;
		return (T)this;
	}
	
	public <T extends Protocol> T cloneBase() {
		Protocol p=(Protocol)ReqContext.getRequestBean();
		if(p!=null) {
			return this.cloneBaseFrom(p);
		}else {
			RequestUtils requestUtils=ReqContext.getRequestUtis();
			HttpServletRequest httpServletRequest=ReqContext.getHttpServletRequest();
			UIFrameAppConfig uiFrameAppConfig=BeanGet.getBean(UIFrameAppConfig.class);
			AkaFrameProperties.Protocol protocolInfo = uiFrameAppConfig.getProtocolInfo(this.getClass());
			String clssName = this.getClass().getName();
			if(protocolInfo!=null) {
				String suf = StringUtils.trimLeadingString(clssName, protocolInfo.getPackageName());
				String strs[] = suf.split("\\.");
				String ver = strs[1];
				String modName = strs[2];
				this.namespace = protocolInfo.getNamesapce();
				this.moduleName = modName;
				this.protocol = this.getClass().getSimpleName();
				this.remoteIp = "";
				this.requestid = "";
				this.ver = ver;
				this.udid = "";
				this.sourceId = "";
				this.timestamp = null;
				this.userid = "";
				return (T) this;
			}
			throw new ServiceException("无法找到协议的配置信息!");

		}
	}
}
