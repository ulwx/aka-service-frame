package com.github.ulwx.aka.frame.process;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;


public class DebugProcess extends Processor {
	private static Logger log = Logger.getLogger(DebugProcess.class);

	@Override
	public BaseRes process(HttpServletRequest request,ProcessContext context) throws Exception {

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
