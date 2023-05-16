<%@page import="com.github.ulwx.aka.frame.utils.JspLog"%>
<%@page import="com.github.ulwx.aka.frame.protocol.utils.UlFrameSignatures"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.github.ulwx.aka.frame.protocol.res.JSPFormBean.PostInputParam"%>
<%@page import="com.github.ulwx.aka.frame.protocol.req.Protocol"%>
<%@page import="com.github.ulwx.aka.frame.UIFrameAppConfig"%>
<%@page import="java.util.List"%> 
<%@page import="com.github.ulwx.aka.frame.protocol.res.JSPFormBean"%>
<%@page import="com.ulwx.tool.EscapeUtil"%>
<%@page import="com.ulwx.tool.ObjectUtils"%>
<%@page import="com.ulwx.tool.StringUtils"%>
<%@page import="com.github.ulwx.aka.frame.protocol.res.BaseRes"%>
<%@page import="com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BaseRes bean = (BaseRes) request.getAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_FORM_BEAN);
    Protocol proObj=(Protocol)request.getAttribute(UiFrameConstants.PROTOCOL_KEY_JSP_PRO_OBJ);
	JSPFormBean jspBean = null;
	String postUrl = "";
	String redirect = "";
	if (bean instanceof JSPFormBean) {
		jspBean = (JSPFormBean) bean;
		if (jspBean.getStatus() == 0) {
			redirect = jspBean.data.redirectUrl;
		
			if (StringUtils.hasText(redirect)) {
				if (redirect.indexOf("?") > 0) {
					redirect = redirect + "&ret="
							+ EscapeUtil.escapeUrl(ObjectUtils.toJsonString(bean), "utf-8");
				} else {
					redirect = redirect + "?ret="
							+ EscapeUtil.escapeUrl(ObjectUtils.toJsonString(bean), "utf-8");
				}
		
				response.sendRedirect(redirect);
				return;
		
			}else{
			%>错误【<%=bean.getError()%>】<%=bean.getMessage()%><%
			}
		}else {
			//如果成功
			if (StringUtils.hasText(jspBean.data.submitUrl)) {
			
				postUrl = jspBean.data.submitUrl.trim();
				if(postUrl.startsWith("/")){
					postUrl=request.getContextPath()+""+postUrl;
				}
			} else {
				postUrl =request.getContextPath()+""+ UiFrameConstants.NOTIFY_FORM_DEFAULT_SUBMIT;
			}
		   %>
		
			<!DOCTYPE html>
			<html>
			<head>
			
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<meta http-equiv="pragma " content="no-cache ">
			<meta http-equiv="cache-control " content="no-cache ">
			<meta http-equiv="expires " content="0 ">
			<meta
				content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
				name="viewport">
			<%
			 String root=request.getContextPath()+"/jsp/protocol/form_files";
			%>
			<title><%=jspBean.data.pageTitle %></title>

			<link rel="icon"
				href="<%=root%>/favicon.ico.png"
				type="image/png" >
			<link rel="shortcut icon"
				href="<%=root%>/favicon.ico.png"
				type="image/png" >
			<link type="text/css" href="<%=root%>/style_v13.css" rel="stylesheet">
			<link rel="stylesheet" type="text/css"
				href="<%=root%>/easyui/themes/metro-gray/easyui.css" />
			
			<link rel="stylesheet" type="text/css"
				href="<%=root%>/easyui/themes/icon.css">
			<link rel="stylesheet" type="text/css"
				href="<%=root%>/easyui/themes/color.css">
			
			<script type="text/javascript" src="<%=root%>/jquery.js"></script>
			<script type="text/javascript"
				src="<%=root%>/easyui/jquery.easyui.min.js"></script>
			<script type="text/javascript"
				src="<%=root%>/easyui/locale/easyui-lang-zh_CN.js"></script>
			<script type="text/javascript"
				src="<%=root%>/form.js"></script>
			<script type="text/javascript">
				
				function ok() {
		            $('#form').form('submit',{
		                onSubmit:function(){
		                   return $(this).form('enableValidation').form('validate');
		                  
		                }
		            });
					
				}
				
				$(document).ready(function() {
					$('#form').form({
						ajax:false
					});
				})
			</script>
			</head>
			<body>
			
				<div class="header">
					<div class="div-m">
			
						<h1 class="logo">
							<a href="<%=UIFrameAppConfig.Property.getValue(proObj.infkey, "shouye.url") %>" class="logoImg"> <img src="<%=root%>/logo.png">
							</a>
						</h1>
					</div>
				</div>
			
				<!--主体开始-->
				<div class="container" style="min-height: 449px;">
					<div class="infoBar">
						<div class="div-m">
							<span class="subtitle"><%=jspBean.data.pageTitle %></span>
							<ul>
								<li class="first">平台名称：粤商财富</li>
								<li>平台公司名称：广州市粤商股权投资基金管理有限公司</li>
							</ul>
						</div>
					</div>
					<div class="div-m" style="min-height: 257px;">
					 <% 
					  List<PostInputParam> postParam = jspBean.data.postParam;
					  PostInputParam requestnoKey = new PostInputParam("text", "", "requestno", jspBean.data.interParm.getRequestNo());
					  requestnoKey.isHidden = true;
					  postParam.add(requestnoKey);
					  List<PostInputParam> topParms=new ArrayList<PostInputParam>();
					  for (PostInputParam item : postParam) {
						   
						     if(!item.inTopBox ){
						    	 continue;
						     }
						     topParms.add(item);
					  }
					  if(topParms.size()>0){
					 %>
						<div class="infoCard">
						  
							<ul>
							   <%
							   
							   for (PostInputParam item : topParms) {
							   %>
								<li class="blue"><%=item.chName %>：<%=item.value %></li>
							  <%} %>
							</ul>
						</div>
					<% }%>
						<form id="form" method="post" action="<%=postUrl%>">
							<div class="formBlock">
								<h2 class="title blue"><%=jspBean.data.formTitle %></h2>
								<ul>
							    <%
								List<PostInputParam> parms = jspBean.data.postParam;
								String reqCodeArgName = UiFrameConstants.NOTIFY_REQ_CODE_ARGNAME;
								JspLog.debug("reqCodeArgName="+reqCodeArgName+",proObj.getInfkey()="+proObj.getInfkey());
								Map<String,String> signMap=new TreeMap<String,String>();
								for (PostInputParam item : parms) {
									String inputType = item.inputType;
									String chName = item.chName;
									String enName = item.engName;
									boolean isHidden = item.isHidden;
									boolean readonly = item.readonly;
									boolean require = item.require;
									String[] validateType = item.validateType;
									String value = item.value;
									String requireStr = require ? "required" : "";
									String typeStr=item.inputType;
									
									if(isHidden){
										typeStr="hidden";
									}
									String dataOption="";
									if(isHidden){
										dataOption=dataOption+","+"hidden:true";
									}
									if(require){
										dataOption=dataOption+","+"required:true";
									}
									if(readonly){
										dataOption=dataOption+","+"readonly:true";
									}
									if(validateType!=null && validateType.length>0){
										dataOption=dataOption+",validType:"+ObjectUtils.toStringUseFastJson(validateType, false, true, false);
									}
									dataOption=StringUtils.trimLeadingString(dataOption, ",");
									if(isHidden || readonly){
										signMap.put(enName, value);
										signMap.put(enName+"_options", dataOption);
									}
									
							   %>
						       
						      <% if(!isHidden){%><li><label><%=chName%></label> <% }
						       if (inputType.equals("text")) {	
						    	   if(readonly){%>
						    		 <input <%=typeStr%> id="<%=enName%>" class="inpReadText easyui-validatebox" 
												name="<%=enName%>" value="<%=value%>"  data-options="<%=dataOption%>" /> 
									 <input type="hidden" name="<%=enName%>_options" value="<%=dataOption%>" />
						    	<% }else{
							     %> 
									 <input <%=typeStr%> id="<%=enName%>" class="inpText easyui-validatebox" 
										name="<%=enName%>" value="<%=value%>"  data-options="<%=dataOption%>" /> 
									 <input type="hidden" name="<%=enName%>_options" value="<%=dataOption%>" />
								 <%}
						    	   }%>
								<%if(!isHidden){%></li><% }	
								
							   }%>
									<li><label>&nbsp;</label> <input type="button"
										class="submitBtn" id="nextButton" onclick="ok()"
										value="确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定"></li>
								</ul>
							</div>
						<input  type="hidden" id="<%=reqCodeArgName %>" name="<%=reqCodeArgName%>" 
						 value="<%=EscapeUtil.escapeHtml(jspBean.data.interParm.getReqCode().toReqCodeString() )%>" />
					    <% 
					     String r=StringUtils.trim(jspBean.data.redirectUrl);
					     if(StringUtils.hasText(r)){
					    	 r=EscapeUtil.escapeHtml(r);
					     }
					    %>
						<input  type="hidden" id="r" name="r"  value="<%=r%>" />
						<%
						  String signKey=UIFrameAppConfig.Property.getValue(proObj.getInfkey(), UIFrameAppConfig.Property.REQUEST_SIGN_KEY);
						 
						  String toSignStr=ObjectUtils.toStringUseFastJson(signMap,false, true, false);
						  JspLog.debug("toSignStr="+toSignStr);
						  String signStr=UlFrameSignatures.signature(signKey, toSignStr);
						%>
						<input  type="hidden" id="signStr" name="signStr"  value="<%=signStr%>" />
						</form>

						<div class="clear"></div>
					</div>
			
					<div class="tips" style="height: 52px; visibility: hidden">温馨提示：</div>
				</div>
			
				<div class="footer">
					<p class="tc"></p>
				</div>
				<div id="mask" style="display: none; height: 604px;"></div>
			
			
			</body>
			</html>
			
	  <%}

	} else {//不为JSPFormBean
	  %> 错误【<%=bean.getError()%>】<%=bean.getMessage() %><%
	}
%>