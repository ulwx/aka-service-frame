package com.github.ulwx.aka.frame;


import com.github.ulwx.aka.frame.protocol.CallBackType;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.ReqCodeParm;

import java.util.HashMap;
import java.util.Map;

public class InterParam {
    //协议对象
    private Protocol protocol;
    //业务请求订单号
    private String requestNo;

    //接口请求类型
    private String interType=UiFrameConstants.InterType.Direct;
    //用户id
    private String userId="";

    private String platformNo="";
    //关联订单号
    private String refReqNo="";

    //扩展，用于存在log里，回调时可以取出
    private Map<String,Object> extMap=new HashMap<>();
    //请求reqCode信息
    private ReqCodePart reqCodePart;

    public ReqCodePart getReqCodePart() {
        return reqCodePart;
    }

    public void setReqCodePart(ReqCodePart reqCodePart) {
        this.reqCodePart = reqCodePart;
    }

    public String getUserId() {
        return userId;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    /**
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInterType() {
        return interType;
    }

    /**
     * 设置接口请求类型
     * @param interType  具体含义见{@link UiFrameConstants.InterType}
     */
    public void setInterType(String interType) {
        this.interType = interType;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public String getRefReqNo() {
        return refReqNo;
    }

    /**
     * @param refReqNo 关联订单号
     */
    public void setRefReqNo(String refReqNo) {
        this.refReqNo = refReqNo;
    }


    public Protocol getProtocol() {
        return protocol;
    }

    /**
     *
     * @param protocol 协议对象
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }


    public InterParam() {
        // TODO Auto-generated constructor stub
    }

    /**
     * ReqCode的格式为  [ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace]
     */
    public static class ReqCodePart{
        //解析编码，匹配aka.frame.notify-request.parsers里的项的键值
        private String parseCode = "";
        //0:无回调  1：异步通知  2：网关回调  3：本地回调
        private CallBackType callType = CallBackType.NO_CALLBACK;
        //服务名称
        private String serviceName = "";
        //扩展参数
        private String extStr="";

        /**
         *  ReqCode的格式为  [ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace]
         * @param parseCode 解析编码，匹配aka.frame.notify-request.parsers里的项的键值
         * @param callType 0:无回调  1：异步通知  2：网关回调  3：本地回调 查看@link
         * @param serviceName 服务名称
         * @param extStr
         */
        public ReqCodePart(String parseCode, CallBackType callType, String serviceName, String extStr) {
            super();
            this.parseCode = parseCode;
            this.callType = callType;
            this.serviceName = serviceName;
            this.extStr = extStr;
        }
        /**
         * 格式为  [ver]@[moduleName]@[parseCode]@[callType]@[serviceName]@[extStr]@[namespace]
         * @param parseCode  解析编码，匹配aka.frame.notify-request.parsers里的项的键值
         * @param callType 0:无回调  1：异步通知  2：网关回调  3：本地回调
         * @param serviceName 服务名称，一般对应协议处理类的名称
         */
        public ReqCodePart(String parseCode, CallBackType callType, String serviceName) {
            super();
            this.parseCode = parseCode;
            this.callType = callType;
            this.serviceName = serviceName;
        }

        /**
         *
         * @param serviceName 服务名称，一般对应协议处理类的名称
         */
        public ReqCodePart(String serviceName) {
            this.serviceName=serviceName;
        }
        public String getParseCode() {
            return parseCode;
        }
        public void setParseCode(String parseCode) {
            this.parseCode = parseCode;
        }
        public CallBackType getCallType() {
            return callType;
        }
        public void setCallType(CallBackType callType) {
            this.callType = callType;
        }
        public String getServiceName() {
            return serviceName;
        }
        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
        public String getExtStr() {
            return extStr;
        }
        public void setExtStr(String extStr) {
            this.extStr = extStr;
        }


    }

    public ReqCodeParm getReqCode() {
        ReqCodeParm rp=new ReqCodeParm(protocol, this.reqCodePart.parseCode,
                this.reqCodePart.callType, this.reqCodePart.serviceName, this.reqCodePart.extStr);
        return rp;
    }

}

