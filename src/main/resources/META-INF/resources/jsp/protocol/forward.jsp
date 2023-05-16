<%@page import="com.github.ulwx.aka.frame.protocol.res.ForwardBean"%>
<%@page import="com.github.ulwx.aka.frame.protocol.req.Protocol"%>
<%@page import="com.github.ulwx.aka.frame.UIFrameAppConfig"%>
<%@page import="com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants"%>
<%@page import="com.github.ulwx.aka.frame.protocol.res.CallbackBean"%>

<%@page import="com.github.ulwx.aka.frame.protocol.res.BaseRes"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
 <%
	BaseRes bean=(BaseRes)request.getAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_FORWARD_BEAN);
    Protocol  proObj=(Protocol)request.getAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ);
	ForwardBean forwardBean = null;
	String redirect = "";
	if(bean instanceof ForwardBean){
		forwardBean=(ForwardBean)bean;
		redirect=forwardBean.getRedirectURL();
	}
	String msg="";
	if(StringUtils.hasText(redirect)){
		response.sendRedirect(redirect); 
		return;
	
	}else{
		msg=bean.getMessage();
  }
  
  %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="Author" contect="jyd"/>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes">
<%
   String pic_link=UIFrameAppConfig.Property.getValue(proObj.getInfkey(), UIFrameAppConfig.Property.pic_link);
   if(StringUtils.hasText(pic_link)){
 %>
    <link rel="shortcut icon" type="image/png"
          href="<%=pic_link%>"/>
 <%} %>
   
    <title></title>

    <style>
        body {
            line-height: 24px;
            font: 14px/1.5 "PingFang SC";
            background-color: #f5f7fc;
            position: relative;
            max-width: 540px;
            margin: 0 auto;
        }

        .tac {
            text-align: center;
        }

        .dib {
            display: inline-block;
        }

        .fz0 {
            font-size: 0px !important;
        }

        .bora240 {
            border-radius: 240px;
            -webkit-border-radius: 240px;
            -moz-border-radius: 240px;
            -ms-border-radius: 240px;
            -o-border-radius: 240px;
        }

        .layui-btn {
            background: transparent;
            border: 1px solid #4777f3;
            color: #4777f3;
            padding: 0;
            font-size: .512rem;
            letter-spacing: 1px;
            transition: initial;
            -webkit-transition: initial;
            opacity: 1;
            color: #fff !important;
            border: 0 !important;
            background: linear-gradient(to right, #71befd, #4778f3);
            background: -ms-linear-gradient(to right, #71befd, #4778f3);
            background: -moz-linear-gradient(to right, #71befd, #4778f3);
            background: -webkit-linear-gradient(to right, #71befd, #4778f3);
            background: -o-linear-gradient(to right, #71befd, #4778f3);
            vertical-align: middle;
            cursor: pointer;
        }

        .layui-btn-pure {
            background: transparent;
            border: 1px solid #4778f3 !important;
            color: #4778f3 !important;
        }

        .message-main img {
            margin-top: 3.12rem
        }

        .message-main img.icon-success, .message-main img.icon-fail {
            width: 3.952rem
        }

        .message-main .message-desc {
            font-size: 0.512rem;
            line-height: 0.512rem;
            color: #555;
            margin: 1.056rem auto 0;
        }

        .message-main .btn-div {
            margin-top: 1.056rem;
        }

        .message-main .btn-div .layui-btn-w260 {
            padding: 0 .544rem;
            height: 1.248rem;
            line-height: 1.248rem;
            text-align: center;
            min-width: 3.84rem;
        }

        @media screen and (min-width: 750px) {
            html, body {
                height: 100%;
            }

            .message-main {
                position: absolute;
                left: 50%;
                width: 100%;
                margin-left: -270px;
                top: 50%;
                margin-top: -120px;
            }

            .message-main img.icon-success, .message-main img.icon-fail {
                width: 148px;
                margin-top: 0;
            }

            .message-main .message-desc {
                font-size: 16px;
                line-height: 16px;
                margin-top: 43px;
            }

            .message-main .btn-div {
                margin-top: 51px;
            }

            .message-main .btn-div .layui-btn {
                -webkit-border-radius: 0;
                -moz-border-radius: 0;
                border-radius: 0;
                font-size: 16px;
                width: 268px;
                height: 40px;
                line-height: 40px;
            }
        }

        /*# sourceMappingURL=maps/message.css.map */

    </style>
    <script type="text/javascript" language="javascript">
        var adaptive = function (num, width) { //相对单位rem
            var doc = document,
                win = window,
                num = num || 50,
                width = width || 320,
                docElem = doc.documentElement,
                resizeEvent = "orientationchange" in window ? "orientationchange" : "resize",
                recalc = function () {
                    var clientWidth = docElem.clientWidth;
                    if (!clientWidth) return;
                    docElem.style.fontSize = num * ((clientWidth > 750 ? 540 : clientWidth) / width) + 'px';
                };
            recalc();
            if (!doc.addEventListener) return;
            win.addEventListener(resizeEvent, recalc, false);
            doc.addEventListener("DOMContentLoaded", recalc, false);
        }
        adaptive(1, 750 * 12 / 750);
    </script>

</head>
<body>
<div class="message-main tac fz0">
 <%

 String pic_tip_suc=UIFrameAppConfig.Property.getValue(proObj.infkey, UIFrameAppConfig.Property.pic_tip_suc);
 String pic_tip_fail=UIFrameAppConfig.Property.getValue(proObj.infkey, UIFrameAppConfig.Property.pic_tip_fail);

 if(bean.getStatus()==1){
	 if(StringUtils.hasText(pic_tip_suc)){
 %>
    <img class="icon-success"
         src="<%=pic_tip_suc %>"
         alt="">
 <%}}else{
	 if(StringUtils.hasText(pic_tip_fail)){
%>
    <img style="display: none;" class="icon-fail"
         src="<%=pic_tip_fail %>"
         alt="">
 <%}} %>
    <div class="message-desc"><%=msg %></div>
    <div class="btn-div fz0">
        <a href="<%=UIFrameAppConfig.Property.getValue(proObj.infkey, "shouye.url")%>">
            <button class="layui-btn layui-btn-w260 layui-btn-pure bora240" type="button">访问首页</button>
        </a>
    </div>
</div>
</body>
</html>