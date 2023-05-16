
<%@page import="com.github.ulwx.aka.frame.protocol.utils.IError"%>
<%@page import="com.github.ulwx.aka.frame.utils.JspLog"%>
<%@page import="com.github.ulwx.aka.frame.UIFrameAppConfig"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>协议</title>

<jsp:include page="/head.jsp" flush="true"></jsp:include>

<style type="text/css">
 .eastBodyClas{
    overflow:hidden
 }
</style>
<script type="text/javascript">
	function openDialog(json){
		 $('#dlg').dialog('open').dialog('center');
		 $('#dlg #content').JSONView(json);
	}
	$(document).ready(function() {
		$('#ff').form({
			ajax:false,
			iframe:true
		});
	});
	function oksendFun2(interfacePrefix,ip){
	   $('body').showLoading();
		var queryURL= $("#url").val() ;
        $("#ifid").attr("src",queryURL); 
        $("#ifid").load(function(){
        	$('body').hideLoading();
        	var content=($("#ifid").contents().find("body").text()).trim();
        	
        	if(content.startWith("{")){
        		var json=eval("("+content+")");
        		content="<pre>"+JsonUti.convertToString(json)+"</pre>";
        		$("#ifid").contents().find("body").html(content);
        	}else{
        		
        	}
        	
        });
        setTimeout(function(){
        	$('body').hideLoading();
        },2000);
		 
	 }
	 function oksubmitfun(interfacePrefix,ip,newWindow){
		 $("#form1 [name='requestid']").val(new Date().format("yyyyMMddHHmmssS"));
		 $("#form1 [name='timestamp']").val(new Date().getTime()+"");
		 var ver=$("#form1 [name='ver']").val();
		 var protocol=$("#form1 [name='protocol']").val();
		 var moduleName=$("#form1 [name='moduleName']").val();
		 $('body').showLoading();
		 var requestURL=window.location.protocol+"//"+ip+"<%=request.getContextPath()%>"+"/req/"+ interfacePrefix+ "/" + ver+"/"+moduleName + "/" + protocol ;
		 $('#form1').attr("action",requestURL);
		 if (newWindow){
			 $('#form1').attr("target", "_blank");
		 }else{
			 $('#form1').attr("target", "ifid");
		 }
		 $('#form1').submit();
		 $("#ifid").load(function(){
			 $('body').hideLoading();
			 var content=($("#ifid").contents().find("body").text()).trim();

			 if(content.startWith("{")){
				 var json=eval("("+content+")");
				 content="<pre>"+JsonUti.convertToString(json)+"</pre>";

				 $("#ifid").contents().find("body").html(content);
			 }
		 });
		 setTimeout(function(){
			 $('body').hideLoading();
		 },3000);

	 }

	function okresetFun() {

		$("#form1")[0].reset();
	}
</script>
</head>

<body style="width:100%;" class="easyui-layout" fit="true">

    <div data-options="region:'east',split:true,title:'响应结果' ,iconCls:'icon-ok',bodyCls:'eastBodyClas'"
     			style="width:320px;border:1px solid #abafb8;overflow:hidden">
		 <iframe id="ifid" name="ifid" frameborder="0" width="100%"
				 height="100%" marginheight="0" marginwidth=0 src="<%=request.getContextPath()%>/jsp/ulwxframe/test/blank.html"></iframe>
    </div> 
     <div data-options="region:'center',title:'协议参数' ,iconCls:'icon-ok'">
     	<jsp:include page="/jsp/ulwxframe/test/protocolform.jsp" flush="true"></jsp:include>
    </div>
    <div id="dlg" class="easyui-dialog" title="类型详情"
		data-options="closed:true,resizable:true"
		style="width: 800px; height: 500px; padding: 10px">
		<div id="content" style="width: 100%; height: 100%">
		
		</div>
	</div>
</body>
</html>