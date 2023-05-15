package com.github.ulwx.aka.frame.servlet.support;

import com.github.ulwx.aka.frame.protocol.req.ICallBack.CallBackType;
import com.github.ulwx.aka.frame.protocol.utils.IError;


public class CallbackBeanFactory {
	public static CallbackBean getCallbackBean(CallBackType callBackType) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			return cb;
		}
		return null;


	}
	public static CallbackBean SUC(CallBackType callBackType,String message) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(message);
			cb.setStatus(1);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(message);
			cb.setStatus(1);
			return cb;
		}
		return null;


	}
	public static CallbackBean SUC(CallBackType callBackType,String message, String operType) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(message);
			cb.data.operType=operType;
			cb.setStatus(1);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(message);
			cb.data.operType=operType;
			cb.setStatus(1);
			return cb;
		}
		return null;


	}
	public static CallbackBean SUC(CallBackType callBackType) {
		return SUC(callBackType,"成功");
	}
	
	
	public static CallbackBean ERR(CallBackType callBackType,String message) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(message);
			cb.setError(IError.COMMON_ERROR);
			cb.setStatus(0);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(message);
			cb.setError(IError.COMMON_ERROR);
			cb.setStatus(0);
			return cb;
		}
		return null;

	}
	
	public static CallbackBean ERR(CallBackType callBackType,String message, String operType) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(message);
			cb.setError(IError.COMMON_ERROR);
			cb.data.operType=operType;
			cb.setStatus(0);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(message);
			cb.setError(IError.COMMON_ERROR);
			cb.data.operType=operType;
			cb.setStatus(0);
			return cb;
		}
		return null;
	}
	public static CallbackBean ERR(CallBackType callBackType,int errorCode) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(IError.get(errorCode));
			cb.setError(errorCode);;
			cb.setStatus(0);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(IError.get(errorCode));
			cb.setError(errorCode);;
			cb.setStatus(0);
			return cb;
		}
		return null;
	}
	
	public static CallbackBean ERR(CallBackType callBackType,int errorCode, String msg) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(IError.get(errorCode,msg));
			cb.setError(errorCode);;
			cb.setStatus(0);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(IError.get(errorCode,msg));
			cb.setError(errorCode);;
			cb.setStatus(0);
			return cb;
		}
		return null;

	}
	public static CallbackBean ERR(CallBackType callBackType,
								   int errorCode, String msg, String operType) {
		if(callBackType==CallBackType.ASYNC_CALLBACK){
			NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
			cb.setMessage(IError.get(errorCode,msg));
			cb.setError(errorCode);
			cb.data.operType=operType;
			cb.setStatus(0);
			return cb;
		}else if(callBackType==CallBackType.GATEWAY_CALLBACK ||
				callBackType==CallBackType.LOCAL_SUBMIT_CALLBACK){
			NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
			cb.setMessage(IError.get(errorCode,msg));
			cb.setError(errorCode);
			cb.data.operType=operType;
			cb.setStatus(0);
			return cb;
		}
		return null;

	}
}
