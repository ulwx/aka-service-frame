package com.github.ulwx.aka.frame.servlet;

public  class ValidationException extends Exception {
	private String argName;

	public String getArgName() {
		return argName;
	}

	public void setArgName(String argName) {
		this.argName = argName;
	}

	public ValidationException(String argName, String message) {
		super(message);
		this.argName = argName;
	}
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
