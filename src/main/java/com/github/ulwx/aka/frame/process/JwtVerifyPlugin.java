package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.utils.TokenInfo;

public interface JwtVerifyPlugin {

	public VerifyResult verify(TokenInfo jwtInfo)throws Exception;

	public static class VerifyResult{
		private boolean success=true;
		private String message;
		private int errorCode;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
	}
}
