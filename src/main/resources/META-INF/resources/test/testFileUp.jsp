<%@ page contentType="text/html; charset=utf-8" language="java"
         errorPage="" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>测试文件上传</title>
    <jsp:include page="/head.jsp" flush="true"></jsp:include>
    
    <script type="text/javascript">
    	// openUpFileDlg(type,fType,id,callback)
    	
    	function openDlg(){
    		 openUpFileDlg(1,1,23456,function(data){
    			//alert("返回--"+ $.toJSON(data));
				/**
				 * @param type : 1：项目相关文件 2：用户相关文件  3:其他
				 * @param fType : 1：图片 2：文件 	
				 * @param id : 如果type=1，则为项目id；如果type=2，则为用户id ；如果type=3，则 id可以不传
				 * @Param callback(data) :data为返回的对象，对象里的属性如下：
				 *  	    relaFilePath: 文件服务器相对路径
				 *   		httpPath: 文件服务器http绝对地址
				 *   		ossPath: 阿里云oss相对路径,
				 *   		ossHttpPath: 阿里云oss相对路径的http绝对地址
				 * @returns
				 */
    			$("<image src='"+data.httpPath+"' />").appendTo("#content");
    			
    		 });
    	}
    </script>
</head>
<body>

<div style="margin:20px" id="content">
   <input type="button" name="upload" value="点击上传图片"  onclick="openDlg()"/>
   
</div>



</body>
</html>
