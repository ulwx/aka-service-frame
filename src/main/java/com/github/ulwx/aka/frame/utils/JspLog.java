package com.github.ulwx.aka.frame.utils;

import org.apache.log4j.Logger;

public class JspLog {
	private static Logger log = Logger.getLogger(JspLog.class);
	
	public static void debug(String str){
		log.debug(str);
	}
	public static void info(String str){
		
		log.info(str); 
	}
	public static void error(String str){
		log.error(str);
	}
	
	public static void debug(String str,Throwable e){
		log.debug(str,e);
	}
	public static void info(String str,Throwable e){
		
		log.info(str,e);
	}
	public static void error(String str,Throwable e){
		log.error(str,e);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
