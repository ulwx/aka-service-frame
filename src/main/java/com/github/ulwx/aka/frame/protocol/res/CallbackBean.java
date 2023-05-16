package com.github.ulwx.aka.frame.protocol.res;

import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.CallBackType;
import com.github.ulwx.aka.frame.protocol.utils.IError;

public  class CallbackBean extends IUFrameBean<CallbackBean> {

    @Override
    public Object getData() {
        // TODO Auto-generated method stub
        return null;
    }

    public static CallbackBean of(CallBackType callBackType) {
        if(callBackType==CallBackType.ASYNC_CALLBACK){
            NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
            return cb;
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
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
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
            NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
            cb.setMessage(IError.get(errorCode,msg));
            cb.setError(errorCode);;
            cb.setStatus(0);
            return cb;
        }
        return null;

    }
    public static CallbackBean ERR(CallBackType callBackType,
                                   int errorCode,
                                   String msg,
                                   String operType) {
        if(callBackType==CallBackType.ASYNC_CALLBACK){
            NotifyStrCallbackBean cb=new NotifyStrCallbackBean();
            cb.setMessage(IError.get(errorCode,msg));
            cb.setError(errorCode);
            cb.data.operType=operType;
            cb.setStatus(0);
            return cb;
        }else if(callBackType==CallBackType.GATEWAY_CALLBACK ){
            NotifyJspCallbackBean cb=new NotifyJspCallbackBean();
            cb.setMessage(IError.get(errorCode,msg));
            cb.setError(errorCode);
            cb.data.operType=operType;
            cb.setStatus(0);
            return cb;
        }
        return null;

    }


    public static class NotifyStrCallbackBean extends CallbackBean {
        public Data data=new Data();

        public static class Data{
            @Comment("操作类型,用于接收端区分")
            public String operType="";
            @Comment("第三方异步请求的成功处理后的响应消息")
            public String sucTxt="SUCCESS";
            @Comment("第三方异步请求的处理失败后的响应消息")
            public String failTxt="FAIL";
            @Comment("扩展信息")
            public Object ext;
        }

        public Data getData() {
            return data;
        }

    }

    public static class NotifyJspCallbackBean extends CallbackBean {
        public Data data=new Data();

        public static class Data{
            @Comment("操作类型,用于接收端区分")
            public String operType="";
            @Comment("扩展信息")
            public Object ext;
        }

        public Data getData() {
            return data;
        }

    }
}
