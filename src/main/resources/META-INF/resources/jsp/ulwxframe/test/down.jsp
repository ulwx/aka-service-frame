<%@ page import="com.github.ulwx.aka.admin.utils.Html2pdf" %>
<%@ page import="com.ulwx.tool.StringUtils" %>
<%@ page import="com.ulwx.tool.FileUtils" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.github.ulwx.aka.admin.utils.CbConstants" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>下载协议pdf文件</title>
</head>
<body>
<div>正在下载.....</div>
</body>
<script>

</script>
</html>
<%

    String url=StringUtils.trimTailString(request.getRequestURL().toString(),"down.jsp")+"exportProtocolList.jsp";
    String fileName="protocol"+new Date().getTime()+".pdf";
    String filePathName=FileUtils.getTempDirectory()+"/"+fileName;
    Html2pdf.html2pdf(url, filePathName);
    request.setAttribute(CbConstants.Action.URL_DOWNLOAD,new File(filePathName));
    request.setAttribute(CbConstants.Action.URL_DOWNLOAD_NAME,fileName);
    request.getRequestDispatcher("/download.jsp").forward(request,response);

%>