package com.github.ulwx.aka.frame.protocol.res;

import com.github.ulwx.aka.frame.annotation.Comment;

public abstract class  BaseRes {

	@Comment("状态，1表示成功， 0表示失败")
	private Integer status=1;
	@Comment("错误码，只有status=0时，error才有意义")
	private Integer error=0;

	@Comment("提示性信息")
	private String message="";
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public abstract Object getData() ;

	
	


}
