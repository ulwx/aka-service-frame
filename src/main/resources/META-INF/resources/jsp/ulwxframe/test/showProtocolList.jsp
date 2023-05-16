<%@page import="com.ulwx.tool.NetUtils" %>
<%@page import="com.github.ulwx.aka.frame.annotation.Condition" %>
<%@page import="com.github.ulwx.aka.frame.annotation.InterfaceTest" %>
<%@page import="com.github.ulwx.aka.frame.annotation.Validate" %>
<%@page import="com.github.ulwx.aka.frame.annotation.Validate.ValidateType" %>
<%@page import="com.github.ulwx.aka.frame.protocol.req.Protocol" %>
<%@page import="com.github.ulwx.aka.frame.protocol.req.ReqType.JsonType" %>
<%@page import="com.github.ulwx.aka.frame.protocol.res.NullDataRes" %>
<%@page import="com.github.ulwx.aka.frame.utils.*" %>
<%@page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils.ClassInfo" %>
<%@page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils.FieldInfo" %>
<%@page import="java.lang.reflect.Field" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@ page import="com.ulwx.type.TInteger" %>
<%@ page import="com.ulwx.tool.ObjectUtils" %>
<%@ page import="com.ulwx.tool.EscapeUtil" %>
<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page import="com.github.ulwx.aka.frame.doc.ProtocolClassInfo" %>
<%@ page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils" %>
<%@ page import="com.github.ulwx.aka.frame.doc.VersionModules" %>
<%@ page import="com.github.ulwx.aka.frame.UIFrameAppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>接口协议列表</title>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/js/jquery.jsonview/jquery.jsonview.css"/>
    <jsp:include page="/head.jsp" flush="true"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/jquery.jsonview/jquery.jsonview.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/jquery.plug/jquery.fileDownload.js"></script>
    <style type="text/css">
        .table .protocol_title {
            background: #DFF0D8 !important;
        }

        .table .header {
            border: 1px solid #D9EDF7 !important;
        }

        .mymenu {
            /* 	overflow: auto!important; */
            background: white;
        }

        .accordion-header {
            border-bottom: 1px solid #84909C;
        }

        .mymenu .mymenu-item {
            display: block;
            height: 45px;
            /* 	min-height: 24px; */
            /* 	line-height: 24px; */
            font-size: 14px;
            color: #15428b;
            text-align: left;
            text-decoration: none;
            padding-top: 3px;
            padding-left: 5px;
            margin-left: 10px;
            margin-right: 5px;
            border-bottom: 1px solid #84909C;
        }

        .mymenu .mymenu-item:hover {
            background: #DCDCDC center repeat-x;
            text-decoration: none;
        }

        .mymenu .mymenu-item.clicked {
            display: block;
            height: 45px;
            /* 	min-height: 24px; */
            /* 	line-height: 24px; */
            font-size: 14px;
            text-align: left;
            text-decoration: none;
            background: #9eb7b6;
            position: relative;
            padding-top: 3px;
            padding-left: 5px;
        }

        .mymenu .mymenu-item.clicked:after {
            display: inline-block;
            content: '';
            margin-top: 3px;
            position: absolute;
            left: 140px;
            top: 3px;
            border: green;
            width: 16px;
            height: 16px;
            background: no-repeat scroll -16px -32px
        }
    </style>
    <script type="text/javascript">

        function openDialog(json) {
            $('#dlg').dialog('open').dialog('center');
            $('#dlg #content').JSONView(json);
        }

        function openDialogJson(json) {
            $('#dlg-json').dialog('open').dialog('center');
            $('#dlg-json #content-json').html(json);
        }
        function clicka(obj, claName, proBH, ver, module,propertyKey,testip) {
            //alert(obj);
            $(".mymenu .mymenu-item").removeClass("clicked");
            $(obj).addClass("clicked");

            var argObj={
                "className" : claName,
                "protocol" : proBH,
                "ver" : ver,
                "moduleName":module,
                "propertyKey":propertyKey,
                "testip":testip
            }
            var send = $.param(argObj);
            var href=$.pageRoot+"/jsp/ulwxframe/test/showOne.jsp?"+send
            $("#one_protocol").attr("src",href);
        }

        function downloadpdf(){

             var d=new Date();
            var href=$.pageRoot+"/jsp/ulwxframe/test/down.jsp?t="+d.getTime();
            var a="<a href='" + href + "'  target='_blank'/>";

            $(a)[0].click();

        }
        $(document).ready(function () {


        });
    </script>
</head>
<body style="width: 100%;" class="easyui-layout" fit="true">

<div data-options="region:'west',split:true" title="协议列表"
     style="width: 260px;">
    <div class="easyui-accordion" fit=true style="width: 100%;">
        <%
            List<ProtocolClassInfo> wholebbs = new ArrayList<ProtocolClassInfo>();
            Map<String, Map<String, String>> properties = UIFrameAppConfig.Property.getPropertis();
            Set<String> keys = properties.keySet();
            for (String key : keys) {

                VersionModules[] VersionModulesList = ProtocolClassUtils.getProtocolBH(key);
                for (int a = 0; a < VersionModulesList.length; a++) {
                    VersionModules versionModules = VersionModulesList[a];
                    List<ModuleInfo> mouleList = versionModules.getModuleList();
                    for (int b = 0; b < mouleList.size(); b++) {
                        ModuleInfo moduleInfo = mouleList.get(b);
                        List<ProtocolClassInfo> bhs = moduleInfo.getClassList();
        %>
        <div class="mymenu"
             title='<%=versionModules.getNamespace()+"-"+moduleInfo.getModuleName() + "(" + versionModules.getVersion() + ")"%>'>
            <%
                for (int m = 0; m < bhs.size(); m++) {
                    ProtocolClassInfo pcInfo = bhs.get(m);
                    wholebbs.add(pcInfo);
                    ClassInfo cinfo = new ClassInfo();
                    Class c = Class.forName(pcInfo.getClassName());
                    ProtocolClassUtils.getClassDesc(c, cinfo);
            %>
            <div class="mymenu-item"
                 onclick="clicka(this,'<%=pcInfo.getClassName()%>','<%=pcInfo.getName()%>',
                         '<%=pcInfo.getVer()%>','<%=pcInfo.getModName()%>','<%=pcInfo.getNamespace()%>','')">
                <div><%=m%>-<%=pcInfo.getName()%>
                    <%
                        if (cinfo.done) {
                    %>
                    [<span style="color:red">[已完成]</span>]
                    <%
                        }
                    %>

                </div>
                <div>&nbsp;<%=cinfo.cname + ""%>
                </div>

            </div>
            <%
                }
            %>
        </div>

        <%
                    }
                }
            }
        %>
    </div>
</div>
<div data-options="region:'center',title:'协议详情',iconCls:'icon-ok',tools:'#ttt'">
 <iframe id="one_protocol" style="margin: 0; padding: 0"
         src="" width="100%" height="98%" frameborder="0" scrolling="auto">

 </iframe>
  <div id="dlg" class="easyui-dialog" title="类型详情"
     data-options="closed:true,resizable:true"
     style="width: 800px; height: 500px; padding: 10px">
    <div id="content" style="width: 100%; height: 100%">

    </div>

  <div id="ttt">
      <a href="javascript:void(0)" onclick="downloadpdf()"
         style="width: 60px;display:inline-block;color: red">协议下载</a>
      <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
  </div>
 </div>

  <div id="dlg-json" class="easyui-dialog" title="响应代码"
     data-options="closed:true,resizable:true"
     style="width: 800px; height: 500px; padding: 10px">
    <pre id="content-json" style="width: 100%; height: 100%"></pre>
  </div>
</div>

<a id="fordownload"  ></a>
</body>
</html>







