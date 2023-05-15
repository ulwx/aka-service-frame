package com.github.ulwx.aka.frame.protocol;

/**
 * 数据交换异常
 * 
 * @author 黄杰
 * 
 */
public class ExchangeException extends RuntimeException {

	
	public static Integer PRO_TOCOL_NAME_NOT_EXIST=404;
	public static Integer PRO_TOCOL_TYPE_NOT_DECIDE=505;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 异常状态码
	 */
	private int code;

	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 构造函数
	 * 
	 * @param status
	 *            状态码
	 * @param msg
	 *            异常信息
	 */
	public ExchangeException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	
	public ExchangeException(int code) {
		this.code = code;
	}
}
