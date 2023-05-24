package com.github.ulwx.aka.frame.protocol;

public enum InterType {
    //下面的两种类型都是主动行为
    OutGateWay ,// 对外网关转接，对接第三方网关接口。要通过主动发送HTTP请求
    OutDirect,// 对外直连转接，对接第三方直连接口。要通过主动发送HTTP请求
    //下面的类型都是被动行为
    JSON,//本地JSON返回接口
    JSPPost,//本地Post提交接口
    Redirect,//本地跳转接口
    STR,//本地返回文本接口
    DownLoad,//本地下载接口
    Forward,//本地forward接口
    CB_GW,//网关回调
    CB_NF,//异步回调
}
