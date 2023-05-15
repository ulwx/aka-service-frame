<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2021/5/6
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String platformUserNo=request.getParameter("platformUserNo");
    String bankcardNo=request.getParameter("bankcardNo");
    String bankcode=request.getParameter("bankcode");
    out.println("platformUserNo="+platformUserNo+",bankcardNo="+bankcardNo+",bankcode="+bankcode);
%>
</body>
</html>
