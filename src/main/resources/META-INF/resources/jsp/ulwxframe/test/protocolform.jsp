<%@page import="com.github.ulwx.aka.frame.UIFrameAppConfig"%>
<%@page import="com.github.ulwx.aka.frame.annotation.InterfaceTest"%>
<%@page import="com.github.ulwx.aka.frame.annotation.Validate"%>
<%@page import="com.github.ulwx.aka.frame.annotation.Validate.ValidateType"%>
<%@page import="com.github.ulwx.aka.frame.protocol.req.Protocol"%>
<%@page import="com.github.ulwx.aka.frame.protocol.req.ReqType.JsonType"%>
<%@page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils"%>
<%@page import="com.github.ulwx.aka.frame.doc.ProtocolClassUtils.FieldInfo"%>
<%@page import="java.io.File"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="com.ulwx.type.TInteger" %>
<%@ page import="com.ulwx.tool.ObjectUtils" %>
<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
	String className=request.getParameter("className"); 
	String protocol=request.getParameter("protocol");
	String ver=request.getParameter("ver");//协议版本号moduleName
	String moduleName=request.getParameter("moduleName");//
	String propertyKey=request.getParameter("propertyKey");//propertyKey
	String ndjh=UIFrameAppConfig.Property.getValue(propertyKey,UIFrameAppConfig.Property.NDJH);
%>
<form id="form1" method="post"  enctype="multipart/form-data">
<input type="hidden" name="ndjh" value="<%=ndjh%>" />
<table class="table" style="font:bold 13px;">

  <%
    String testip=request.getParameter("testip");
    Object obj=Class.forName(className).newInstance();
    Class c = obj.getClass();

	List<Field> list = new ArrayList<Field>();
	List<Class> listClazz=new ArrayList<Class>();
	TInteger superLen=new TInteger();
	Class clazz=c.getSuperclass();
	Field[] curFields = ObjectUtils.getPublicFileds(c, superLen,Protocol.class);;
	//排序修改
	//
	String hiddenHtml="";
	if (curFields != null) {
		
		for (int i = 0; i < curFields.length; i++) {

			int count=1;
			
			Field field = curFields[i];
			String fieldName = field.getName();
			Class<?> fieldType = field.getType();
			Object fvalue = field.get(obj);
			String valueStr="";
			String style="";
			String inputType="text";
			String typeStr= fieldType.getSimpleName();
			String strClass="even";
			if(i/2==0){
				strClass="even";
			}else{
				strClass="odd";
			}
			InterfaceTest test=field.getAnnotation(InterfaceTest.class);
			String defaultVal="";
			
			String[] defaultVals=new String[0];
			if(test!=null){
			   defaultVal= StringUtils.trim(test.value());
			}
			if(fieldName.equals("protocol")){
				defaultVal=protocol;
			}else if(fieldName.equals("ver")){
				defaultVal=ver;
			}
			else if(fieldName.equals("moduleName")){//moduleName
				defaultVal=moduleName;
			}
			if(test!=null && !test.display()){//不显示
				 if(test.hidden()){//表示作为隐藏字段
					 hiddenHtml=hiddenHtml+"<input name='"+fieldName+"' type='hidden' value='"+defaultVal+"' />";
					 continue;
				 }else{
					 fieldName="";
					 //判断是否hidden
					continue;
				 } 
			}

			String notNull = "";
			Validate[] verifys = field.getAnnotationsByType(Validate.class);
			for (Validate verify : verifys) {
				if (verify != null) {
					ValidateType[] validateTypes = verify.value();
					for (ValidateType validateType : validateTypes) {
						if (validateType == ValidateType.NOTNULL) {
							notNull = "*";
						}
					}
				}
			}
			
			if(fieldType==File.class){
				inputType="file";
			}else if(fieldType.isArray()){
				count=2;
				if(StringUtils.hasText(defaultVal)){
					defaultVals=defaultVal.split("\\,");
					
				}
				if(fieldType.getComponentType()==File.class){
					inputType="file";
				}
			}
			FieldInfo fieldFnfo = new FieldInfo();
			ProtocolClassUtils.getFieldDesc(field, fieldFnfo);
			
			if(i>=superLen.getValue()){
				style="color:red";
			}
			for(int n=0; n<count; n++ ){
				if(count==2){
					if(defaultVals!=null && defaultVals.length>=2){
						defaultVal=defaultVals[n];
					}
				}
			%>
			
			<tr class="<%=strClass%>" style="font-size: 13px;font-weight: 700">
			       
			<td class="first" style="<%=style%>"><%=fieldName %><br/>[
			<%
				if (JsonType.class.isAssignableFrom(fieldType) || (fieldType.isArray() &&JsonType.class.isAssignableFrom(fieldType.getComponentType()))) {
						Class fcz=fieldType;
						if(fieldType.isArray()){
							fcz=fieldType.getComponentType();
						}else{
							
						}
						String jsonTypeToJson = ProtocolClassUtils.toJson(fcz, 0, "");
						String pv = protocol + '_' + ver + "_" + moduleName;
			%> <script type="text/javascript">
	 			 var json_req_<%=pv%>=<%=jsonTypeToJson%>;
			  </script> JsonType
			<a href="javascript:void(0);" onclick="openDialog(json_req_<%=pv%>)">(<%=fieldFnfo.type%>)</a> 
			<%
			 	} else {
			 %> <%=fieldFnfo.type %> <%
			 	}
			 %>
			]</td>
			
			<td  style="width:250px">
			<%if(inputType.equals("text")) {%>
			<textarea name="<%=fieldName %>" style="width:90%;height:24px"><%=defaultVal%></textarea> 
			<%}else if(inputType.equals("file")) { %>
			<input name="<%=fieldName %>" type="<%=inputType %>"  value="<%=defaultVal%>" style="width:90%"/> 
			<%} %>
			</td>
			<td style="width:300px"> <span style="color:red"><%=notNull %></span> 
			 <%=fieldFnfo.cname %> <%=fieldFnfo.comment %>
			</td>
			</tr>
		<%
			}
		}
	 }%>
   
   <%=hiddenHtml %>
  <tr style="height:30px"><td colspan="4" align="center" >
   <input  type="button" name="oksend" value="提    交"  onclick="oksubmitfun('<%=propertyKey%>','<%=testip%>');"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input  type="button" name="oksend" value="提交到新窗口"  onclick="oksubmitfun('<%=propertyKey%>','<%=testip%>',1);"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input  type="button" name="okreset" value="重   置"  onclick="okresetFun();"/>
   </td></tr>

  </table>
  
 </form> 
 <form id="form2" method="post" >
  <table class="table">
   <tr>
         <td colspan="4" align="center"  ><textarea id="url" name="url" style="width:100%;height:100px"></textarea></td> 
         
  </tr>
   <tr>
         <td colspan="4" align="center"  style="height:30px"> 
          <input  type="button" name="submit2" value="提交"  onclick="oksendFun2();"/> 
         
         </td> 
  </tr>
  </table>
 
 </form>
 
