<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>查看日志</title>
<jsp:include page="/head.jsp"  flush="true"></jsp:include>
<script>
	<%String UserReqRecSno = StringUtils.trim((String) request.getParameter("UserReqRecSno"));%>
	$(document).ready(function(){
		formReset("#form1");
		load();
		$("body").css("visibility", "visible");
		$('#status').combobox('setValue','1');
	});

	function reloadTable(){
		dlg.reloadTable();
	}

	var dlg=null;
	function init(dialog){
		dlg=dialog;
	}

	function load(){
		<%
			System.out.println(">>>"+UserReqRecSno);
		%>
		<%if (StringUtils.hasText(UserReqRecSno)) {%>
			var json=syncGetJSON("<%=request.getContextPath()%>/frame/log_UserReqRec_getJSON.action?UserReqRecSno=<%=UserReqRecSno%>");
			if(json.code==1){
				//$('#form1')[0].reset();
				//$("#res").val(json.content.ResponseContent);
				var obj = eval("(" + json.content.ResponseContent + ")");
				$("#res").html(JsonUti.convertToString(obj));
			}else if(json.code==0){
				//$.messager.alert("提示","对象不存在!","info");
				showMsg("对象不存在。");
			}else{
				//$.messager.alert("提示","网络访问失败。","info");
				showMsg("网络访问失败。");
			}
		<%}%>
	   $("#win").show();
	}

	function alter(){
		$("#win").show();
		dlg.autoSize();
	}

	function cancel(type) {
		dlg.close();
	}

</script>
</head>
<body style="visibility: hidden;width:100%;">
	<div id="win">
		<form id="form1" method="post">
			<table class="table">
				<tr class="even">
					<td>
						<div class="easyui-layout" style="width: 700px;height: 600px;">
						<div data-options="region:'north',split:true" title="json" style="height: 600px;width: 700px;">
							<pre id="res">
							</pre>
					    </div>
					</div>
					</td>
				</tr>
				<tr class="page-bottombar">
					<td>
						<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>

</html>