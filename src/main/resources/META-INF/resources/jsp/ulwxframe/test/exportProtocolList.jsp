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
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8"/>
    <title>接口协议列表</title>
    <jsp:include page="/head.jsp" flush="true"></jsp:include>
    <style type="text/css">
        table tr th {
            /*word-wrap: break-word; 内容将在边界内换行*/
            word-break: break-all; /*字内断开*/
        }

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
        $(document).ready(function () {

        });


        function openDialog(json) {
            $('#dlg').dialog('open').dialog('center');
            $('#dlg #content').JSONView(json);
        }

        function openDialogJson(json) {
            $('#dlg-json').dialog('open').dialog('center');
            $('#dlg-json #content-json').html(json);
        }
    </script>
</head>
<body style="width: 100%;" class="easyui-layout" fit="true">

<div data-options="region:'center',title:'协议详情',iconCls:'icon-ok'">

    <div id="potocols">

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

                        for (int m = 0; m < bhs.size(); m++) {
                            ProtocolClassInfo pcInfo = bhs.get(m);
                            wholebbs.add(pcInfo);
                            ClassInfo cinfo = new ClassInfo();
                            Class c = Class.forName(pcInfo.getClassName());
                            ProtocolClassUtils.getClassDesc(c, cinfo);
                        }
                    }
                }
            }
            for (int m = 0; m < wholebbs.size(); m++) {
                ProtocolClassInfo pcInfo = wholebbs.get(m);

                String className = pcInfo.getClassName();
                String protocol = pcInfo.getName();
                String module = pcInfo.getModName();
                String propertyKey = pcInfo.getNamespace();
                String ver = pcInfo.getVer();//协议版本号
                Object obj = Class.forName(className).newInstance();
                Class c = obj.getClass();
                TInteger superLen = new TInteger();
                String resClassName = ProtocolClassUtils.getResPrococolClassName(propertyKey, ver, protocol, module, className);
                Class<?> resClass = null;
                try {
                    resClass = Class.forName(resClassName);
                } catch (Exception e) {
                    resClass = NullDataRes.class;
                }
                Field[] curFields = ObjectUtils.getPublicFileds(c, superLen, Protocol.class);

                int superClassFieldLen = superLen.getValue();
                ClassInfo cinfo = new ClassInfo();
                ProtocolClassUtils.getClassDesc(c, cinfo);
                String interfacePrefix = UIFrameAppConfig.Property.getProtocolNamespace(c);
                if (interfacePrefix == null || interfacePrefix.isEmpty()) {
                    interfacePrefix = "/intf";
                }
                String url = "http://" + NetUtils.getHostIP() + ":" + request.getServerPort() + ""
                        + request.getContextPath() + interfacePrefix;

                String requestName = "/" + ver + "/" + module + "/" + protocol + "";
        %>
        <%--Include non-heading element as bookmark (level 4)--%>
        <%--<strong data-pdf-bookmark="4">Foo bar</strong>--%>
        <%--Specify bookmark name--%>
        <%--<tr data-pdf-bookmark="5" data-pdf-bookmark-name="Bar baz">...</tr>--%>
        <%--Exclude individual heading from bookmarks--%>
        <%--<h3 data-pdf-bookmark="none">Baz qux</h3>--%>
        <div style="margin: 40px 40px 15px 40px">
            <h3  data-pdf-bookmark="1"><%=m+1%>、<%=cinfo.cname%><%=protocol%></h3>
        </div>

        <div id="protocol_<%=protocol + '_' + ver + "_" + module%>"
             class="protocol"
             style="width: 90%; border: 1px solid #c3e8d1; background-color: #ebf7f0; margin: 10px 40px">

            <table class="table"
                   style="font-size: 12px; width: 100%; font-weight: 700;">
                <tbody>
                <tr class="header">
                    <th class="first" style="width: 120px; height: 40px">协议名称：</th>
                    <th><%=protocol%>
                    </th>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px;">中文名称：</td>
                    <td><%=cinfo.cname%>
                    </td>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px;">作者：</td>
                    <td><%=cinfo.author%>
                        <%

                            if (cinfo.done) {
                        %>
                        [<span style="color:red">已完成</span>]
                        <%
                        } else {
                        %>
                        [<span>开发中</span>]
                        <%
                            }
                        %>
                    </td>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px">模块【版本】：
                    </td>
                    <td><%=module%>[<%=ver%>]</td>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px">URL（<%=cinfo.method%>）：
                    </td>
                    <td><%=requestName%>
                    </td>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px">服务器URL前缀：</td>
                    <td><%=url%>
                    </td>
                </tr>
                <tr class="even">
                    <td class="first protocol_title" style="width: 120px">注释：</td>
                    <td><%=cinfo.comment%>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="table"
                   style="font-size: 12px; width: 100%; font-weight: 700; ">
                <thead>

                <tr class="header">
                    <th class="first" style="width: 140px">参数名称</th>
                    <th style="width: 140px">中文名称</th>
                    <th style="width: 120px">类型</th>
                    <th style="width: 100px">是否必须</th>
                    <th>描述</th>

                </tr>
                </thead>
                <%
                    if (curFields != null) {

                        int ln = 0;
                        for (int i = 0; i < curFields.length; i++) {

                            Field field = curFields[i];
                            FieldInfo fieldFnfo = new FieldInfo();
                            ProtocolClassUtils.getFieldDesc(field, fieldFnfo);
                            String fieldName = field.getName();
                            Class<?> fieldType = field.getType();
                            Object fvalue = field.get(obj);
                            String valueStr = "";
                            String style = "";

                            String typeStr = fieldType.getSimpleName();
                            String strClass = "even";

                            InterfaceTest test = field.getAnnotation(InterfaceTest.class);
                            String defaultVal = "";

                            if (test != null) {
                                defaultVal = StringUtils.trim(test.value());
                            }
                            if (fieldName.equals("protocol")) {
                                defaultVal = protocol;
                            } else if (fieldName.equals("ver")) {
                                defaultVal = ver;
                            }
                            if (test != null && !test.display()) {//不显示
                                continue;
                            }

                            if (ln++ % 2 == 0) {
                                strClass = "even";
                            } else {
                                strClass = "odd";
                            }

                            if (i >= superClassFieldLen) {
                                style = "color:black";
                            } else {//忽略父域
                                continue;
                            }
                            String notNull = "否";
                            Validate[] verifys = field.getAnnotationsByType(Validate.class);
                            for (Validate verify : verifys) {
                                // Validate verify = field.getAnnotation(Validate.class);
                                if (verify != null) {
                                    ValidateType[] validateTypes = verify.value();
                                    for (ValidateType validateType : validateTypes) {
                                        if (validateType == ValidateType.NOTNULL) {
                                            notNull = "是";
                                        }
                                    }
                                }
                            }
                %>

                <tr style="<%=style%>" class="<%=strClass%>">

                    <td class="first" style="width: 120px"><%=fieldFnfo.name%>
                    </td>
                    <td><%=fieldFnfo.cname%>
                    </td>
                    <td>
                        <%
                         if (JsonType.class.isAssignableFrom(fieldType)) {
                                String jsonTypeToJson = ProtocolClassUtils.toJson(fieldType, 0, "");
                                String pv = protocol + '_' + ver + "_" + module;
                        %>
                        <script type="text/javascript">
                            var json_req_<%=pv%> =<%=jsonTypeToJson%>;
                        </script>
                        JsonType
                        <a href="javascript:void(0);" onclick="openDialog(json_req_<%=pv%>)">(<%=fieldFnfo.type%>)</a>
                        <%
                        } else if (fieldType.isArray() && JsonType.class.isAssignableFrom(fieldType.getComponentType())) {
                            String jsonTypeToJson = ProtocolClassUtils.toJson(fieldType.getComponentType(), 0, "");
                            String pv = protocol + '_' + ver + "_" + module;
                        %>
                        <script type="text/javascript">
                            var json_req_<%=pv%> =<%=jsonTypeToJson%>;
                        </script>
                        JsonType
                        <a href="javascript:void(0);" onclick="openDialog(json_req_<%=pv%>)">(<%=fieldFnfo.type%>)</a>
                        <%
                        } else {
                        %>
                        <%=fieldFnfo.type%> <%
                        }
                    %>
                    </td>

                    <td><%=notNull%>
                    </td>
                    <td><%=fieldFnfo.comment%>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
            <%
                if (resClass != null) {

                    String json = ProtocolClassUtils.toJson(resClass, 2, "");

                    String jsonEscape = EscapeUtil.escapeJavaScript(json);
                    String pv = protocol + '_' + ver + "_" + module + "_0";

                    String[] jsons = new String[]{json};
                    String[] pvs = new String[]{pv};
                    String[] jsonEscapes = new String[]{jsonEscape};

                    Condition condition = resClass.getAnnotation(Condition.class);
                    if (condition != null) {
                        Class<?>[] rcc = condition.value();
                        if (rcc != null && rcc.length > 0) {
                            jsons = new String[rcc.length];
                            pvs = new String[rcc.length];
                            jsonEscapes = new String[rcc.length];
                            for (int f = 0; f < rcc.length; f++) {
                                jsons[f] = ProtocolClassUtils.toJson(rcc[f], 2, "");
                                pvs[f] = protocol + '_' + ver + "_" + module + "_" + f;
                                jsonEscapes[f] = EscapeUtil.escapeJavaScript(jsons[f]);
                            }
                        }
                    }

                    for (int n = 0; n < jsons.length; n++) {
            %>
            <div>
                <table class="table" style="width: 100%;">
                    <tr class="header">
                        <th class="first" style="width: 120px">响应<%=n + 1 %>：</th>
                        <th>
                        </th>
                    </tr>
                </table>
                <div class="protocol_res"
                     id="<%=protocol + '_' + ver + "_" + module+"_"+n%>" style="font-size: 12px;font-weight: 700;">
                    <%=jsons[n].replace(" ","&nbsp;").replace("\n","<br/>")%>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
        <%
            }
        %>

    </div>


    <div id="dlg" class="easyui-dialog" title="类型详情"
         data-options="closed:true,resizable:true"
         style="width: 800px; height: 500px; padding: 10px">
        <div id="content" style="width: 100%; height: 100%">

        </div>
    </div>

    <div id="dlg-json" class="easyui-dialog" title="响应代码"
         data-options="closed:true,resizable:true"
         style="width: 800px; height: 500px; padding: 10px">
        <pre id="content-json" style="width: 100%; height: 100%"></pre>
    </div>
</div>


</body>
</html>







