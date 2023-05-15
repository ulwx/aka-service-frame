
<%@page import="com.github.ulwx.aka.frame.utils.*"%>
<%@page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils.ClassInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page import="com.github.ulwx.aka.frame.doc.ProtocolClassInfo" %>
<%@ page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils" %>
<%@ page import="com.github.ulwx.aka.frame.doc.VersionModules" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>接口数据查看工具工具</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/jquery.jsonview/jquery.jsonview.css" />
<style type="text/css">
/********** start menu list style ************/
.mymenu {
	/* 	overflow: auto!important; */
	background: white;

}
.accordion-header{
	border-bottom:1px solid #84909C !important;
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
	border-bottom:1px solid #84909C;
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
.tabs-panels .panel-htop .panel-body{
   overflow:hidden;
}
</style>
<jsp:include page="/head.jsp" flush="true"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.jsonview/jquery.jsonview.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.plug/ext.date.js"></script> 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.plug/easyui.ext.tab.js"></script> 
<script type="text/javascript">
	
	 $(document).ready(function(){
		$('#tt').tabs({   
		    border:false,   
		    plain :true,
		    fit: true,
		    onSelect:function(title,index){

		    },
		    onBeforeClose:function(title,index){
		    	var target = this;
		    	var frame = easyui.ext.tab.findFrameInTab('#tt',title);
		    	return true;	
		    	
		    },
		    onAdd:function(title,index){

            }
	    }); 
		
		easyui.ext.tab.bindTabEvent('#tt');
		easyui.ext.tab.bindTabMenuEvent('#tt');
	 });

	 

	
	function clicka(obj,claName, proBH,ver,module,propertyKey,testip) {
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
		var href="jsp/ulwxframe/test/oneProtocol.jsp?"+send
		easyui.ext.tab.addTab('#tt',proBH,href,1)

	}


</script>
</head>
<body style="width: 100%;" class="easyui-layout" fit="true">

	<div data-options="region:'west',split:true" title="协议列表"
		style="width:270px;">
		   <div class="easyui-accordion" fit=true style="width:100%;">
		   <%
		   	List<ProtocolClassInfo> wholebbs = new ArrayList<ProtocolClassInfo>();
   		   	Map<String,Map<String,String>> properties=UIFrameAppConfig.Property.getPropertis();
   		   	Set<String> keys=properties.keySet();
   		   	for(String key:keys){ 
   		   	   VersionModules[] VersionModulesList = ProtocolClassUtils.getProtocolBH(key);
   		   	   String testip=UIFrameAppConfig.Property.getValue(key,UIFrameAppConfig.Property.TESTIP);
   		   	   if(StringUtils.isEmpty(testip)){
   		   			String reqUrl=request.getRequestURL().toString();
   		   			String reqUri=request.getRequestURI();
   		   			String rootURl=StringUtils.trimTailString(reqUrl, reqUri);
   		   			
   		   			testip= StringUtils.trimLeadingString(rootURl, "http://");
   		   			testip=StringUtils.trimLeadingString(testip, "https://"); 
   		   			
   		   		}
   		   		
   		   		for (int a = 0; a < VersionModulesList.length; a++) {
   		   			VersionModules versionModules = VersionModulesList[a];
   		   			List<ModuleInfo> mouleList = versionModules.getModuleList();
   		   			for (int b = 0; b < mouleList.size(); b++) {
   		   				ModuleInfo moduleInfo = mouleList.get(b);
   		   				List<ProtocolClassInfo> bhs = moduleInfo.getClassList();
		   %>
			<div class="mymenu" title='<%=versionModules.getNamespace()+"-"+moduleInfo.getModuleName() + "(" + versionModules.getVersion() + ")"%>'>
				<%
	
				for (int m = 0; m < bhs.size(); m++) {
					ProtocolClassInfo pcInfo = bhs.get(m);
					wholebbs.add(pcInfo);
					ClassInfo cinfo = new ClassInfo();
					Class c=Class.forName(pcInfo.getClassName());
					ProtocolClassUtils.getClassDesc(c, cinfo);
				%>
				<div class="mymenu-item" 
				onclick="clicka(this,'<%=pcInfo.getClassName()%>','<%=pcInfo.getName()%>','<%=pcInfo.getVer()%>','<%=pcInfo.getModName()%>','<%=pcInfo.getNamespace()%>','<%=testip%>')">
				  <div><%=m%> - <%=cinfo.cname%></div>
				  <div>&nbsp;&nbsp;&nbsp;&nbsp;<%=pcInfo.getName()+""%>
				   <%		   
					   if(cinfo.done){
						   %>
						   [<span style="color:red">已完成</span>]
						   <% 
					   }else{
						   %>
						   [<span >开发中</span>]
						   <% 
					   }
				 	%>
				  </div>
				
				</div>
				<%
					}
				%>
			</div>
			
			<%}
			}
		}
		%>
		</div>
	</div>
	<div data-options="region:'center',title:'',iconCls:'icon-ok'">
		<div id="tt" class="easyui-tabs" data-options="fit:true" >
		</div>
	</div>
	<div id="tt-mm" class="easyui-menu" style="width: 150px;">
		<div id="tt-mm-tabclose">关闭</div>
		<div id="tt-mm-tabcloseall">关闭全部</div>
		<div id="tt-mm-tabcloseother">关闭其他</div>
		<div class="menu-sep"></div>
		<div id="tt-mm-tabcloseright">关闭右侧标签</div>
		<div id="tt-mm-tabcloseleft">关闭左侧标签</div>
		<div class="menu-sep"></div>
	</div>
</body>
</html>