<%@ page contentType="text/html; charset=utf-8" language="java"
         errorPage="" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>测试文件上传活动</title>
    <jsp:include page="/head.jsp" flush="true"></jsp:include>
    
    <script type="text/javascript">
    	// openUpFileDlg(type,fType,id,callback)
    	
    	function openDlg(){
   			//alert("返回--"+ $.toJSON(data));
			/**
			 * @Param callback(data) :data为返回的对象，对象里的属性如下：
			 *  	    relaFilePath: 文件服务器相对路径
			 *   		httpPath: 文件服务器http绝对地址
			 *   		ossPath: 阿里云oss相对路径,
			 *   		ossHttpPath: 阿里云oss相对路径的http绝对地址
			 * @returns
			 */
			 openUpActivityDlg(function(data){
 
    			$("<span>"+data.httpPath+"</span>").appendTo("#content");
    			
    		 });
    	}
    </script>
</head>
<body>

<div style="margin:20px" id="content">
   <input type="button" name="upload" value="点击上传活动zip包"  onclick="openDlg()"/>
   
</div>



</body>
</html>
