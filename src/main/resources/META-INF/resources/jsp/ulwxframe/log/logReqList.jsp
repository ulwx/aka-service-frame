<%@ page contentType="text/html; charset=utf-8" language="java"
%>
<!DOCTYPE >
<html>
<head>
    <meta charset="UTF-8">
    <title>请求日志列表</title>
    <jsp:include page="/head.jsp" flush="true"></jsp:include>
    <style type="text/css">
        .textbox-label {
            width: 60px;
        }

        .datagrid-toolbar {
            border-width: 0 0 0 0
        }
    </style>
    <script type="text/javascript">

        function getDataGridColums() {
            var columns=[[
                {field: 'id', title: '编号', align: 'center',width:80,sortable:true},
                {field: 'requestNo',title: '业务请求订单号', align: 'center',width:145,sortable:true},
                {field: 'type', title: '类型', align: 'center',width:60,sortable:true},
                {field: 'className', title: '处理类', align: 'center',width:150,sortable:true},
                {field: 'serviceName', title: '接口名称', align: 'center',width:150,sortable:true},
                {field: 'classReqArgs', title: '请求参数', align: 'center',width:80,sortable:true,
                	formatter:function(value,row,index){
                        return "<a href='javascript:showMsg(\""+index+"\",\"classReqArgs\",\"请求参数\")'>查看</a>";
                    }   
                },
                {field: 'platformNo', title: '平台编码', align: 'center',width:120,sortable:true},
                {field: 'userDevice', title: '用户终端设备类型', align: 'center',width:170,sortable:true},
                {field: 'platformUserNo', title: '平台用户id', align: 'center',width:120,sortable:true},
                {field: 'reqData', title: '请求数据体', align: 'center',width:100,sortable:true,
                    formatter:function(value,row,index){
                        return "<a href='javascript:showMsg(\""+index+"\",\"reqData\",\"请求数据体\")'>查看</a>";
                    }   
                },
                {field: 'keySerial', title: '证书序号', align: 'center',width:120,sortable:true},
                {field: 'returnStr', title: '响应字符串', align: 'center',width:100,sortable:true,
                	formatter:function(value,row,index){
                		return "<a href='javascript:showMsg(\""+index+"\",\"returnStr\",\"响应字符串\")'>查看</a>";
                    }  	
                },
                {field: 'extStr', title: '扩展字符串', align: 'center',width:120,sortable:true},
                {field: 'doneStatus', title: '处理状态', align: 'center',width:100,sortable:true,
                	formatter:function(value,row,index){
                        if(value=="23"){
                        	return "失败";
                        }else if(value=="24"){
                        	return "成功";
                        }else if(value=="134"){
                        	return "处理中";
                        }
                    }   	
                },
                {field: 'status', title: '接口返回的状态', align: 'center',width:120,sortable:true},
                /* {field: 'logid', title: '日志', align: 'center',width:120,sortable:true}, */
                {field: 'addTime', title: '添加时间', align: 'center',width:170,sortable:true},
                {field: 'refReqNo', title: '关联请求流水号', align: 'center',width:150,sortable:true},
                {field: 'handlerTimes', title: '处理的毫秒数', align: 'center',width:120,sortable:true},
                {field: 'ip', title: 'ip', align: 'center',width:120,sortable:true}
            ]];

            return columns;
        }

        $(document).ready(function () {
            var gridUrl = "<%=request.getContextPath()%>/frame/log_Log_getLogReqListJSON.action";
            initDataGrid(".logReqList", gridUrl, getQueryParm(), getDataGridColums(), {fitColumns: false});
            $("body").css("visibility", "visible");
        });

        function lookup() {
            $('.logReqList').datagrid("load", getQueryParm());
        }

        function getQueryParm() {
            var parmObject = $(".searchForm").serializeObject();
            return parmObject;
        }

        function reloadGrid() {
            $(".logReqList").datagrid("reload");
        }
        
        function showMsg(index,param,title){
        	var rows = $('.logReqList').datagrid('getRows');
            var row = rows[index];
        	var html = "<div><textarea style='width: 520px;height: 320px;margin-top: 20px;margin-left: 32px;resize: none;'>"+row[param]+"</textarea></div>";
        	var opt = {
                width : 600,
                height : 400,
                title : title,
                self : false,
                autoHeight : false,
                content : html,
                resizable : true,
                modal : true,
                shadow : true
        	}
            easyui.ext.open(opt);
        }
    </script>
</head>
<body class="easyui-layout commonPage" style="visibility: hidden">
<div region="center" border="false" title="" data-options="headerCls:'headerClass'">
    <div id="top" class="tools-box">
        <form class="searchForm" onsubmit="return false;">
            <select id="sType" class="easyui-combobox" name="sType" style="width:90px;" editable="false">
                <option value="requestNo" selected="selected">业务请求订单号</option>
                <option value="serviceName">接口名称</option>
                <option value="className">处理类</option>
                <option value="status">接口返回的状态</option>
            </select>
            <input type="text" class="easyui-textbox" name="condition" style="width: 80px;"/>
                           处理状态
            <select id="doneStatus" name="doneStatus" class="easyui-combobox" style="width:120px;"
                data-options="required:false">
                <option value="">全部</option>
                <option value="23">失败</option>
                <option value="24">成功</option>
                <option value="134">处理中</option>
            </select>
            <input data-options="label:'时间',labelAlign:'right'" type="text"
                   style="width:180px;height:26px;" class="easyui-datebox" name="startTime">
            --
            <input type="text" style="width:130px;height:26px;" class="easyui-datebox" name="endTime">
            <a class="search easyui-linkbutton" onclick="lookup()">查询</a>
        </form>
    </div>
    <table id="logReqList" class="logReqList">
    </table>
</div>
</body>
</html>