package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.BaseResBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.ulwx.tool.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.util.Collection;
import java.util.Map;

public class InitProcessor extends Processor {
	private static Logger log = Logger.getLogger(InitProcessor.class);

	@Override
	public BaseRes process(HttpServletRequest request, ProcessContext context) throws Exception {

		String queryStr = request.getQueryString();
		try {
			String contentType = StringUtils.trim(request.getContentType());
			if (contentType.contains("multipart/")) {
				Collection<Part> parts = request.getParts();
				for (Part part : parts) {
					String partContentType = part.getContentType();
					if (partContentType != null) {
						String argName=part.getName();
						
						String fileName = part.getSubmittedFileName();
						if(StringUtils.isEmpty(fileName)) continue;
						log.debug("partName-"+part.getName()+",fileName="+fileName);
						
						String saveName = System.currentTimeMillis() +"_"+RandomUtils.getRandomNumberString(6)+ "."
							    + FileUtils.getTypePart(fileName);
						String uploadDir=FileUtils.getTempDirectory().getAbsolutePath();
						if(!FileUtils.isDirExist(uploadDir)){
							FileUtils.makeDirectory(uploadDir);
						}
						File file=new File(uploadDir+"/"+saveName);
						
						part.write(file.getAbsolutePath());
						
						file=new File(file.getAbsolutePath());
						if(file.exists()){
							context.setFile(argName, file,fileName,partContentType);
						}
						long siez = part.getSize();
						for (String headerName : part.getHeaderNames())
							log.info(headerName + " : " + part.getHeader(headerName));
					}
					// 出来文件上传的情况
				}
			}

			String bodyStr="";
			if (contentType.contains("application/json")) {
				try {
					bodyStr = IOUtils.toString(request.getInputStream(), "utf-8", true);
					context.setBody(bodyStr);
				} catch (Exception e) {
					log.error("" + e, e);
				}
			}
			Map<String,String[]> parameterMap=request.getParameterMap();
			context.putAll(parameterMap);
			String uri = request.getRequestURI();
			log.debug("uri=[" + uri + "]");
			log.debug("query string=[" + queryStr + "]");
			// 解析uri获取协议号和协议版本
			int last_p = uri.lastIndexOf("/");
			int last_m = uri.lastIndexOf("/", last_p - 1);
			String  module= uri.substring(last_m + 1, last_p);
			String protocolName = uri.substring(last_p + 1);
			int last_v = uri.lastIndexOf("/", last_m - 1);
			String ver = uri.substring(last_v + 1, last_m);
			// 解析module
			log.debug("ver=" + ver);
			log.debug("protocol=" + protocolName);
			log.debug("module=" + module);
			context.setString(UiFrameConstants.PROTOCOL_REQ_PARM_BN, protocolName);
			context.setString(UiFrameConstants.PROTOCOL_REQ_PARM_VER, ver);
			context.setString(UiFrameConstants.PROTOCOL_REQ_PARM_MOD_NAME, module);
			// 设置query str参数 ，要考虑到post 的情况
			context.setString(UiFrameConstants.PROTOCOL_REQ_QUERY_STR, queryStr);
			if (log.isDebugEnabled()) {
				try {
					log.debug("parmater Map=" + ObjectUtils.toString(parameterMap));
				} catch (Exception e) {
					log.error("", e);
				}
			}

		} catch (Exception e) {
			log.error("" + ObjectUtils.toString(context),e);
			return BaseResBean.ERROR("解析参数出错！");
		}

		return null;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub


	}

}
