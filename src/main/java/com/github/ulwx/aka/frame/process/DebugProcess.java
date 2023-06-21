package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component("com.github.ulwx.aka.frame.process.DebugProcess")
@Order(1)
public class DebugProcess extends ActionSupport implements FrameProcess{
	private static Logger log = Logger.getLogger(DebugProcess.class);

	@Override
	public String process(HttpServletRequest request,
						  ActionMethodInfo actionMethodInfo, RequestUtils context) {

		String queryStr=request.getQueryString();
		if (log.isInfoEnabled()) {

			Enumeration headerNames = request.getHeaderNames();
			String headerValues = "";
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				headerValues = headerValues + headerName + ":"
						+ request.getHeader(headerName) + ";";

			}
			// log.debug();
			String url = StringUtils.trim(request.getRequestURL().toString());
			log.debug("request URL[" + url+"");
			log.debug("HTTP Headers:" + headerValues);
			log.debug("++++++++query String[" + queryStr+"]");
		}
		String ContentType = StringUtils.trim(request.getContentType());
		log.debug("++++ContentType="+ContentType);
		
		return null;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "aa_bb_cc_ _dd";
		System.out.println(ArrayUtils.toJsonString(s.split("_")));

	}

}
