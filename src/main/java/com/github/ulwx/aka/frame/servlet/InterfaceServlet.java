package com.github.ulwx.aka.frame.servlet;

import com.github.ulwx.aka.frame.process.ProcessContext;
import com.github.ulwx.aka.frame.process.ProcessorManager;
import com.github.ulwx.aka.frame.protocol.Exchange;
import com.github.ulwx.aka.frame.protocol.req.*;
import com.github.ulwx.aka.frame.protocol.res.*;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.ulwx.tool.CollectionUtils;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口servlet
 * 
 */
@MultipartConfig(maxFileSize = 52428800,maxRequestSize = 52428800,fileSizeThreshold = 0)
@WebServlet(name = "InterfaceServlet", urlPatterns = "/req/*")
public class InterfaceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(InterfaceServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) {
		ServletOutputStream out = null;
		long startTime = System.currentTimeMillis();
		String protocolid = "";
		String requestURL = "";
		int contentLength = 0;
		RequestUtils ru = null;
		String result = null;
		
		BaseRes retBean=null;
		try {
			ProcessContext pc=new ProcessContext();
			log.debug("request++++++++++++++++++++++");
			// 非下载协议
			String tempStr=StringUtils.trimLeadingString(request.getRequestURI(), request.getContextPath()+"/");
			String strs[]=tempStr.split("/");
			String namespaceKey=strs[1];
			log.debug("namespaceKey="+namespaceKey);
			pc.putToGenArgsAppend(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE, namespaceKey);

			BaseRes errorBean = ProcessorManager.instance.process(request, pc);
			
			if (errorBean == null) {
				ru = pc.getRequest();////////

				protocolid = ru.getString(UiFrameConstants.PROTOCOL_REQ_PARM_BN);
				if(StringUtils.isEmpty(protocolid)){
					throw new Exception("协议号为空！");
				}

				log.debug("map=" + CollectionUtils.toString(ru.getrParms()));
				String requestQuery = ru.getString(UiFrameConstants.PROTOCOL_REQ_QUERY_STR);
				requestURL = request.getRequestURL() + "?" + requestQuery;

				log.debug("request URL=" + requestURL);
				ru.setObject(UiFrameConstants.PROTOCOL_REQ_REQUEST, request);
				ru.setObject(UiFrameConstants.PROTOCOL_REQ_RESPONSE, resp);
				// 调用业务处理逻辑
				retBean = Exchange.gen(ru);
				Object reqObj=ru.getObject(UiFrameConstants.PROTOCOL_REQ_OBJ);
				if(reqObj!=null ) {
					result=getResult(reqObj,retBean,request,resp);
				}
			}else{
				result=ObjectUtils.toJsonString(errorBean);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			contentLength = 0;
			result = ObjectUtils.toJsonString(BaseResBean.ERROR(ex.getMessage()));
		}
		resp.setContentType("application/json;charset=utf-8");
		if (StringUtils.hasText(result)) {
			try {
				contentLength = result.getBytes("utf-8").length;
				resp.setHeader("Content-Length", contentLength + "");
				out = resp.getOutputStream();
				out.write(result.getBytes("utf-8"));
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error(e,e);
			}

		}
		log.debug("+++final : return size=" + contentLength);
		if(retBean!=null) {
			log.debug("+++final : retBean" + ObjectUtils.toStringUseFastJson(retBean));
		}
		log.debug("+++final : json" + result);
		log.debug("====协议号:" + protocolid + "-响应时间：" + (System.currentTimeMillis() - startTime) + " 毫秒");

	}

	private String  getResult(Object reqObj,BaseRes retBean ,HttpServletRequest request, HttpServletResponse resp) throws Exception {
		String result="";
		if(reqObj instanceof JSPProtocal ) {
			
			String forwardURL=UiFrameConstants.FORWARD_POST_JSP;
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_POST_BEAN, retBean);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ, reqObj);
            dispatcher.forward(request, resp);
            result="";
		}else if(reqObj instanceof JSPFormProtocal ) {
			
			String forwardURL=UiFrameConstants.FORWARD_FORM_JSP;
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_FORM_BEAN, retBean);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ, reqObj);
            dispatcher.forward(request, resp);
            result="";
		}
		else if(reqObj instanceof ForwardProtocal ) {
			
			String forwardURL=UiFrameConstants.FORWARD_TO_JSP;
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_FORWARD_BEAN, retBean);
			request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ, reqObj);
            dispatcher.forward(request, resp);
            result="";
		}
		else if(reqObj instanceof RedirectProtocal) {
			if(retBean instanceof RedirectBean) {
				RedirectBean bean=(RedirectBean)retBean;
				resp.sendRedirect(bean.getRedirectURL());
				result="";
			}else {
				throw new Exception(retBean.getMessage());
			}
		}
		else if(reqObj instanceof JSONProtocal) {
			///
			result=ObjectUtils.toJsonString(retBean);
		}else if(reqObj instanceof STRProtocal) {
			if(retBean instanceof STRBean) {
				STRBean strBean=(STRBean)retBean;
				String returnStr=strBean.data.retrunString;
				result=returnStr;
			}else {
				throw new Exception(retBean.getMessage());
			}
			
		}
		else if(reqObj instanceof DownLoadProtocal) {
			if(retBean instanceof DownLoadBean) {
				DownLoadBean downBean=(DownLoadBean)retBean;
				String forwardURL=UiFrameConstants.DOWNLOAD_JSP;
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
				request.setAttribute(UiFrameConstants.URL_DOWNLOAD_FILE, downBean.data.file);
				request.setAttribute(UiFrameConstants.URL_DOWNLOAD_NAME, downBean.data.fileName);
				request.setAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ, reqObj);
	            dispatcher.forward(request, resp);
	            result="";
			}else {
				throw new Exception(retBean.getMessage());
			}

			
		}else {
			result=ObjectUtils.toJsonString(retBean);
		}
		
		return result;
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	public static void main(String[] args) {

	}
}
