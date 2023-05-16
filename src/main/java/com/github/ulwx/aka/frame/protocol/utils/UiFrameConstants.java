package com.github.ulwx.aka.frame.protocol.utils;

import com.ulwx.tool.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class UiFrameConstants {

	// 协议处理类用到的常量
	public static String PROTOCOL_REQ_GATEWAY_KEY = "pro_req_key";
	public static String PROTOCOL_REQ_GATEWAY_IS_CMWAP = "pro_req_is_cmnet";
	public static String PROTOCOL_REQ_CARRIER = "pro_req_carrier";
	public static String PROTOCOL_REQ_GATEWAY_PHONE = "pro_req_phone_gateway";
	public static String PROTOCOL_RES_OBJ = "protocol_res_obj";
	public static String PROTOCOL_REQ_OBJ = "protocol_req_obj";
	public static String PROTOCOL_REQ_REQUEST = "HttpServletRequest";
	public static String PROTOCOL_REQ_RESPONSE = "HttpServletResponse";
	public static String PROTOCOL_REQ_JWTINFO = "protocol_req_jwtinfo";
	public static String PROTOCOL_REQ_QUERY_STR = "pro_req_query_str";
	public static String PROTOCOL_REQ_BODY_STR = "pro_req_query_str";

	public static String PROTOCOL_REQ_PARM_MOD_NAME = "moduleName";
	public static String PROTOCOL_REQ_PARM_BN = "protocol";
	public static String PROTOCOL_REQ_PARM_VER = "ver";
	public static String PROTOCOL_REQ_PARM_USER = "userid";
	public static String PROTOCOL_REQ_PARM_UDID = "udid";
	public static String PROTOCOL_REQ_PARM_TEST = "test";
	public static String PROTOCOL_REQ_NAME_SPACE = "namespace";
	public static String PROTOCOL_REQ_REMOTE_IP = "remoteIp";
	public static String PROTOCOL_REQ_NDJH = "ndjh";

	public static String PROTOCOL_KEY_JSP_POST_BEAN = "jspPostBean";
	public static String PROTOCOL_KEY_JSP_FORM_BEAN = "jspFormBean";
	public static String PROTOCOL_KEY_JSP_FORWARD_BEAN = "jspForwardBean";
	
	public static String PROTOCOL_KEY_JSP_PRO_OBJ = "jspFormBean-proObj";
	
	public static String FORWARD_POST_JSP = "/jsp/protocol/post.jsp";
	public static String FORWARD_FORM_JSP = "/jsp/protocol/form.jsp";
	public static String FORWARD_TO_JSP = "/jsp/protocol/forward.jsp";
	public static String DOWNLOAD_JSP = "/jsp/protocol/download.jsp";
	
	
	public static String NOTIFY_CALLBAK_JSP = "/jsp/protocol/callback.jsp";
	public static String NOTIFY_CALLBACK_BEAN = "notify.callback.bean";
	public static String NOTIFY_CALLBACK_BEAN_INTER_KEY = "notify.callback.bean.inter.key";
	public static String NOTIFY_REDIRECT_URL = "notify.redirect.url";
	public static String NOTIFY_FORM_DEFAULT_SUBMIT="/notify/intf.do";

	public final static String URL_DOWNLOAD_FILE = "ownload_file";
	public final static String URL_DOWNLOAD_NAME = "download_name";

	// reqCode请请求参数名称，格式为:<ver>@<moduleName>@<parseCode>@<callType>@<serviceName>@<extStr>@<infKey>
	// 回调类型 ：1-异步回调 2-网关回调 3-本地窗体提交回调
	public final static String NOTIFY_REQ_CODE_ARGNAME = "rq";

	public static class InterType {
		public static String GateWay = "OutGW";// 对外网关转接，对接第三方网关接口
		public static String Direct = "OutDirect";// 对外直连转接，对接第三方直连接口
		public static String JSON="JSON";//本地JSON返回接口
		public static String JSPPost="JSPPost";//本地Post提交接口
		public static String Redirect="Redirect";//本地跳转接口
		public static String STR="STR"; //本地返回文本接口
		public static String DownLoad="DownLoad";//本地下载接口
		public static String Forward="Forward";//本地forward接口
		public static String NONE="None";//未知
		public static String CallBack= "CallBack"; //回调，含异步回调和网关回调
	}
	public static class HandleStatus {

		public static int init = 0;// 初始
		public static int suc = 24;// 处理成功
		public static int fail = 23;// 处理失败
		public static int doing = 134;// 处理中

	}

	public static void main(String[] args) {
		List<Integer> ss = new ArrayList<Integer>();
		// List<Integer> ssw2;
		ss.add(1);
		ss.add(66);
		ss.add(3);
		ss.add(4);
		// ssw2=ss.subList(2, 3);
		System.out.println(ObjectUtils.toJsonString(ss));

	}
}
