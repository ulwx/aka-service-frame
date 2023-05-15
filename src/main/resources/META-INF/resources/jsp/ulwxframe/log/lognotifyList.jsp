<%@ page contentType="text/html; charset=utf-8" language="java"
%>
<!DOCTYPE >
<html>
<head>
    <meta charset="UTF-8">
    <title>日志列表</title>
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
                /* {field: 'ck', checkbox: true,  align: 'center',width:100}, */
                {field: 'id', title: '编号', align: 'center',width:80,sortable:true},
                {field: 'requestNo',title: '请求流水号', align: 'center',width:150,sortable:true},
                {field: 'serviceName', title: '接口名称', align: 'center',width:120,sortable:true},
                {field: 'responseType', title: '回调类型', align: 'center',width:170,sortable:true},
                {field: 'className', title: '处理类名', align: 'center',width:120,sortable:true},
                {field: 'logid', title: '日志id', align: 'center',width:100,sortable:true},
                {field: 'platformNo', title: '平台编码', align: 'center',width:100,sortable:true},
                {field: 'platformUserNo', title: '平台用户id', align: 'center',width:140,sortable:true},
                {field: 'respData', title: '通知的数据体', align: 'center',width:100,
                    formatter:function(value,row,index){
                        var num = 1
                        return "<a style='color:#056dae' onclick='opens("+index+","+num+")'>查看</a>"
                    }
                },
                {field: 'code', title: '响应里的code', align: 'center',width:120,sortable:true},
                {field: 'status', title: '响应里的status', align: 'center',width:120,sortable:true},
                {field: 'errorCode', title: '错误码', align: 'center',width:120,sortable:true},
                {field: 'errorMessage', title: '错误信息', align: 'center',width:100,sortable:true,
                    formatter:function(value,row,index){
                        var num = 2
                        return "<a style='color:#056dae' onclick='opens("+index+","+num+")'>查看</a>"
                    }
                },
                {field:"returnStr",title:'响应字符串',align: 'center',width:100,sortable:true,
                    formatter: function(value,row,index){
                        var num = 3
                        return "<a style='color:#056dae' onclick='opens("+index+","+num+")'>查看</a>"
                    }
                },
                {field: 'addTime', title: '创建时间', align: 'center',width:140,sortable:true},
                {field: 'ip', title: 'ip', align: 'center',width:140,sortable:true},
            ]];

            return columns;
        }

        function opens(index,num){
            var rows=$(".lognotifyList").datagrid("getRows");
            var row=rows[index];
            if(num == 1){
                $('#dlg2').html(row.respData);
            }
            if(num == 2){
                $('#dlg2').html(row.errorMessage);
            }
            if(num == 3){
                $('#dlg2').html(row.returnStr);
            }
            $("#dlg2").dialog('open');
        }

        $(document).ready(function () {
            $("#dlg2").dialog('close');
            var gridUrl = "<%=request.getContextPath()%>/frame/log_Log_getLogNotifyListJSON.action";
            initDataGrid(".lognotifyList", gridUrl, getQueryParm(), getDataGridColums(), {fitColumns: false});
            $("body").css("visibility", "visible");
        });

        function lookup() {
            $('.lognotifyList').datagrid("load", getQueryParm());
        }

        function getQueryParm() {
            var parmObject = $(".searchForm").serializeObject();
            return parmObject;
        }

        function reloadGrid() {
            $(".lognotifyList").datagrid("reload");
        }
    </script>
</head>
<body class="easyui-layout commonPage" style="visibility: hidden">
<div region="center" border="false" title="" data-options="headerCls:'headerClass'">
    <div id="top" class="tools-box">
        <form class="searchForm" onsubmit="return false;">
            <select id="query" class="easyui-combobox" name="query" style="width:120px;" editable="false">
                <option value="requestNo" selected="selected">requestNo</option>
                <option value="serviceName">serviceName</option>
                <option value="className">className</option>
                <option value="status">status</option>
            </select>
            <input type="text" class="easyui-textbox" name="condition" style="width: 200px;"/>
            <input data-options="label:'时间',labelAlign:'right'" type="text"
                   style="width:180px;height:26px;" class="easyui-datebox" name="startTime">
            --
            <input type="text" style="width:130px;height:26px;" class="easyui-datebox" name="endTime">
            <a class="search easyui-linkbutton" onclick="lookup()">查询</a>
            <div id="dlg2" class="easyui-dialog" title="Basic Dialog" style="width:1000px;height:500px;padding:10px">
            </div>
        </form>

    </div>
    <table id="lognotifyList" class="lognotifyList">
    </table>
</div>
</body>
</html>