
<%@page import="com.github.ulwx.aka.frame.protocol.utils.IError"%>
<%@page import="com.github.ulwx.aka.frame.utils.JspLog"%>
<%@page import="com.github.ulwx.aka.frame.utils.UIFrameAppConfig"%>
<%@page import="java.util.Map"%>
<%@ page import="com.ulwx.tool.ObjectUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>协议介绍</title>

<jsp:include page="/head.jsp" flush="true"></jsp:include>

<style type="text/css">

</style>
<script type="text/javascript">
	$(document).ready(function() {

	});
	

</script>
</head>

<body  class="easyui-layout" fit="true">
<div data-options="region:'center',title:'协议说明'" style="padding:20px">
<h3>一、前言</h3>
<p>
本文档用于描述项目的a交换协议，协议的响应格式为标准json格式。对于数据为图片或文件的交换，则通过URL直接访问下载数据流。
<p>
<h3>二、协议约定</h3>
<pre>
交换协议底层以HTTP1.1协议为载体，客户端通过GET或POST来进行请求。

GET  请求的Content-Type为：text/plain; charset=utf-8。查询参数格式为 a=value1 & b=value2 &… 

POST请求的Content-Type为 ：application/x-www-form-urlencoded;charset=utf-8。请求体为 a=value1 & b=value2 &… 查询参数形式，和GET请求的查询参数一致。

GET和POST里查询参数里的value1，value2，… 要以URL转义，转义字符集为UTF-8。
</pre>
<br/>
请求协议的的基础字段：即每次协议请求所必须涵盖的字段如下：<br/>
<pre>
String  sign;   //签名sign参数，用于防篡改
long  timestamp;  // 用于防重攻击
</pre>
<br/>	
<font color='red'>生成sign参数的防篡改签名算法如下：</font><br/>
<pre>
sign=Md5【 SORT(查询参数字符串 &timestamp=<timestamp>&key=<salt>)】
查询参数字符串： 例如， a=1 & b=2 &...
SORT：按字母排序后的查询参数字符串 ，不包括sign参数
timestamp：为时间戳，客户端取本地时间的long型值
salt ：密钥（盐），客户端和服务器端的共有私钥,客户端和服务器在本地保存，暂定为：xygedgeerxs@345%4
</pre>
<p>
<h3>三、协议jwt认证</h3>
<pre>
客户端和服务器采用标准的jwt认证，客户端必须保存登录后服务器返回的token，后续请必须带上此token到http头里，具体流程如下：

1.	用户在登录认证页面里录入用户，密码等信息
2.	客户端根据用户，密码信息向服务器请求10000协议（登录认证协议）
3.	服务器验证用户密码信息，成功则在http头Authorization里赋值为：  Bearer+空格+<jwt-token>， <jwt-token>为后端生成的jwt token字符串
4.	客户端解析响应，如果响应成功，则把 jw-token保存在本地作为此用户登录后的session-token，后续的所有请求都必须在Authorization头里带上Bearer+空格+<jwt-token>；如果响应失败，则把Authorization置空。
5.	如果下次客户端重新登录，清空本地的jwt-token，并走4步骤。

</pre>
<br/>

<p>
错误码参数说明：
<pre>
<%
	Map<String,Class> errorClassNameMap = UIFrameAppConfig.Property.errorClassMap;
try{
	for(String key:errorClassNameMap.keySet()) {
		IError error = (IError) errorClassNameMap.get(key).newInstance();
		Map<Integer, String> errors = error.getError();
		String json = ObjectUtils.toString(errors);
		out.println("namespace:"+key);
		out.print(json);
	}
}catch(Exception e){
	JspLog.error("",e);
}
%>
</pre>
</div>
</body>
</html>