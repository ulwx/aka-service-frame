<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>接口日志查看</title>
<jsp:include page="/head.jsp"  flush="true"></jsp:include>

<script>
	function reloadTable(){
		$("#jTable").datagrid("uncheckAll");//此两行排除页面删除的记忆功能的bug
		$("#jTable").datagrid("unselectAll");//
		$("#jTable").datagrid("reload");
	}
	$(document).ready(function(){
		$('#RequestTimeStart').datebox('setValue', getDate());
		$('#RequestTimeEnd').datebox('setValue', getDate());
		$('#jTable').datagrid({
			toolbar:"#toolbar",
			iconCls: 'icon-save',
			nowrap: false,
			singleSelect: false,
			striped: true,
			fit: true,
			//fitColumns: true,//此字段为true,则非冰冻字段没有横向滚动条
			url: '<%=request.getContextPath()%>/frame/log_UserReqRec_listJSON.action',
			queryParams: $("#form1").serializeObject(),
			idField: 'UserReqRecSno',
			frozenColumns:[[
                {field: 'Protocol',title: '协议编号',width:50, align: 'center'},
                {field: 'RequestDeviceToken',title: '设备token',width:250, align: 'center'},
                {field: 'RequestMac',title: '用户mac地址',width:80, align: 'center'}
			]],
			columns:[
			[
				{field: 'UserID',title: '用户ID',width:60, 
					formatter: function(value,rec){
						if(value==""){
							return "-";
						}
						return value;
					}
				,align: 'center'},
				{field: 'LogID',title: '日志ID', align: 'center'},
				{field: 'RequestIP',title: '用户IP', align: 'center'},
				{field: 'DeviceModelCode',title: '机型编码', align: 'center'},
				{field: 'DeviceModelName',title: '机型名称', 
					formatter: function(value,rec){
						if(value==""){
							return "-";
						}
						return value;
					}
				,align: 'center'},
			 	{field: 'RequestURL',title: '请求URL',
					formatter: function(value,rec){
						return "<textarea style='width:180px;height:30px'>"+value+"</textarea>";
		        	}
				,align: 'center'},
				{field: 'RequestTime',title: '用户请求时间', align: 'center'},
				{field: 'ProvinceName',title: '省份名称', align: 'center'},
				{field: 'Appname',title: '应用编码', align: 'center'},
				{field: 'Appver',title: '版本号', align: 'center'},
				{field: 'CityName',title: '城市名称', align: 'center'},
				{field: 'ResponseStatus',title: '状态码', align: 'center'},
				{field: 'ResponseError',title: '错误码', align: 'center'},
				{field: 'ResponseMessage',title: '消息',
					formatter: function(value,rec){
						return "<textarea style='width:180px;height:30px'>"+value+"</textarea>";
		        	}
				,align: 'center'},
				{field: 'ResponseContent',title: '响应内容',
					formatter: function(value,rec){
						return "<textarea style='width:180px;height:30px'>"+value+"</textarea><input type='button' value='查看' onclick='show("+rec.UserReqRecSno+")'>";
		        	}
				,align: 'center'},
				{field: 'ResponseSeconds',title: '用户响应耗时', align: 'center'}
			]],
			pagination: true,
			rownumbers: true,
			//pagePosition:"both",
			onRowContextMenu: function(e, rowIndex, rowData){
				$('#jTable').datagrid('unselectAll');
 				$('#jMenu').menu('show', {
 					left: e.pageX,
 					top: e.pageY
 				});
 				$('#jTable').datagrid('selectRow', rowIndex);
 				e.preventDefault();
			}
		});
		
		$("body").css("visibility", "visible");
	});
	
	function show(UserReqRecSno){
		var url = "<%=request.getContextPath()%>/jsp/frame/log/show.jsp?UserReqRecSno="+UserReqRecSno;
		easyui.ext.open({width: 720,height: 620,title: '查看日志详情',
			content: 'url: '+url,
			resizable: true,modal: true,shadow: true , onLoad: function(dlg){  
					   dlg.reloadTable=reloadTable;
			           var win=dlg.window;
			           win["init"](dlg);
            }});
	}

	//多条件组合查询
	function lookup(){
		//使查询窗体的参数变成键值对对象
		var parmObject=$("#form1").serializeObject();
		$('#jTable').datagrid("load",parmObject);
	}

</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">

	<!-- 表实体 -->
	<div region="center" border="false">
	
	<div id="toolbar" title="接口日志查看" border="false"
			style="background:#efefef;padding:10px; overflow:hidden">
		<form id="form1" method="post">
			<label>协议号：</label>
			<input type="text" name="Protocol"
				id="Protocol" style="width: 80px;"/>
				
			<label>token：</label>
			<input type="text" name="RequestDeviceToken"
				id="RequestDeviceToken" style="width: 80px;"/>

			<label>mac地址：</label>
			<input type="text" name="RequestMac"
				id="RequestMac" style="width: 80px;"/>
		
			<label>请求IP：</label>
			<input type="text" name="RequestIP"
				id="RequestIP" style="width: 80px;"/>
		
			<label>开始时间：</label>
			<input class="easyui-datebox" type="text" name="RequestTimeStart"
				id="RequestTimeStart" style="width: 90px;"/>
		
			<label>结束时间：</label>
			<input class="easyui-datebox" type="text" name="RequestTimeEnd"
				id="RequestTimeEnd" style="width: 90px;"/>
			
			<label>省名字：</label>
			<input class="easyui-validatebox" type="text" name="ProvinceName"
				id="ProvinceName" style="width: 80px;"/>
		
			<label>城市名称：</label>
			<input class="easyui-validatebox" type="text" name="CityName"
				id="CityName" style="width: 80px;"/>
		
			<label>应用编码：</label>
			<input class="easyui-validatebox" type="text" name="Appname"
				id="Appname" style="width: 80px;"/>
		
			<label>响应内容：</label>
			<input class="easyui-validatebox" type="text" name="ResponseContent"
				id="ResponseContent" style="width: 80px;"/>
				
			<label>请求URL：</label>
			<input class="easyui-validatebox" type="text" name="url"
				id="url" style="width: 80px;"/>
				
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-search" onclick="lookup()">查询</a>
				
   		 </form>
	</div>
	  <table id="jTable"></table>
	</div>
</body>
</html>