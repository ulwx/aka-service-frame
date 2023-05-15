if($.fn.validatebox){
	
	$.extend($.fn.validatebox.defaults.rules, {
		minlen : { // 判断最小长度
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : '最少输入 {0} 个字符。'
		},
		maxlen : { // 判断最大长度
			validator : function(value, param) {
				return value.length <= param[0];
			},
			message : '最大输入 {0} 个字符。'
		},
		len : { 
			validator : function(value, param) {
				return value.length == param[0];
			},
			message : '最大输入 {0} 个字符。'
		},
		length:{validator:function(value,param){
			var len=$.trim(value).length;
				return len>=param[0]&&len<=param[1];
			},
				message:"输入内容长度必须介于{0}和{1}之间."
			},
		phone : {// 验证电话号码
			validator : function(value) {
				return /^(\d{3,4}\-)?\d{6,8}\-?\d+$/i.test(s);
			},
			message : '格式不正确,请使用下面格式:020-12345678'
		},
		mobile : {// 验证手机号码
			validator : function(value) {
				return /^(\+)?(86)?0?1\d{10}$/i.test(value);
			},
			message : '手机号码格式不正确'
		},
		idcard : {// 验证身份证
			validator : function(value) {
				return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
			},
			message : '身份证号码格式不正确'
		},
		intOrFloat : {// 验证整数或小数
			validator : function(value) {
				return /^\d+(\.\d+)?$/i.test(value);
			},
			message : '请输入数字，并确保格式正确'
		},
		intOrFloatWithPoint : {// 验证整数或小数
			validator : function(value,param) {
				return /^\d+(\.\d{1,2})?$/i.test(value);
			},
			message : '请输入数字，并确保格式正确'
		},
		interestRate : {// 验证利率  2位正整数
			validator : function(value) {
				/*return /^[1-9][0-9]?$/i.test(value);*/
				return /^[0-9][0-9]?([.][0-9]{1,2})?$/i.test(value);
			},
			message : '格式为1到2位正整数或保留最多2位小数!'
		},
		currency : {// 验证货币
			validator : function(value) {
				return /^\d+(\.\d+)?$/i.test(value);
			},
			message : '货币格式不正确'
		},
		money : {// 验证金额  非0开头的金额
			validator : function(value,param) {
				var patern="^[0-9]([0-9]{0,"+(param[0]-1)+"})$";
	        	var m=new RegExp(patern);
	        	if(!m.test(value)){
	        		 $.fn.validatebox.defaults.rules.money.message ="不大于"+param[0]+"位正整数!";
	        	}
	        	return m.test(value);
			},
			message : ''
		},
		
		integer : {// 验证整数
			validator : function(value) {
				return /^-?\d+$/i.test(value);
			},
			message : '请输入整数'
		},
		positiveInteger : {// 验证正整数
			validator : function(value) {
				return /^[+]?[1-9]+\d*$/i.test(value);
			},
			message : '请输入正整数'
		},
		chinese : {// 验证中文
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/i.test(value);
			},
			message : '请输入中文'
		},
		english : {// 验证英语
			validator : function(value) {
				return /^[A-Za-z]+$/i.test(value);
			},
			message : '请输入英文'
		},
		normal : {// 验证普通字符,包括英文数字中划线下划线
			validator : function(value) {
				return /^[a-zA-Z0-9_-]{1,40}$/i.test(value);
			},
			message : '请勿输入英文数字中划线下划线之外的字符'
		},
		unnormal : {// 验证是否包含空格和非法字符
			validator : function(value) {
				return /.+/i.test(value);
			},
			message : '输入值不能为空和包含其他非法字符'
		},
		username : {// 验证用户名
			validator : function(value) {
				return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
			},
			message : '只允许字母开头，6-16字节，字母数字下划线'
		},
		realname : {// 验证真实名称,只能中文
			validator : function(value) {
				return /^[\u4E00-\u9FA5]+$/i.test(value);
			},
			message : '只允许中文'
		},
		content : {// 验证文本,包含中文、字母、数字
			validator : function(value) {
				return /^[\u4E00-\u9FA5A-Za-z0-9]+$/i.test(value);
			},
			message : '只允许中文、字母或数字'
		},
		multiText : {// 多条文本验证
			validator : function(value) {
				return /^((([\u4E00-\u9FA5A-Za-z0-9!@#$%^&*()_=《》<>.、，；?？;‘’\“”\"。,\-\+])*(\r*|\n*|\s*)?)*)?$/i.test(value);
			},
			message : '请勿输入特殊字符'
		},
		zip : {// 验证邮政编码
			validator : function(value) {
				return /^[1-9]\d{5}$/i.test(value);
			},
			message : '邮政编码格式不正确'
		},
		ip : {// 验证IP地址
			validator : function(value) {
				return /\d+.\d+.\d+.\d+/i.test(value);
			},
			message : 'IP地址格式不正确'
		},
		name : {// 验证姓名，可以是中文或英文
				validator : function(value) {
					return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
				},
				message : '输入的值不能包含非法字符'
		},
		carNo:{
			validator : function(value){
				return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value); 
			},
			message : '车牌号码无效（例：粤J12350）'
		},
						carenergin : {
							validator : function(value) {
								return /^[a-zA-Z0-9]{16}$/.test(value);
							},
							message : '发动机型号无效(例：FG6H012345654584)'
						},
		email:{
			validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		},
		message : '请输入有效的电子邮件账号(例：abc@126.com)'    
		},
		msn:{
			validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		},
		message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
		},
		startDate:{
			validator : function(value, param){
				var s=$("#"+param[0]).combobox("getValue");
				if(s!= "" && value != ""){
					return s>= value; 
				}else{
					return true;
				}
			},
			message : '开始日期不能大于结束日期！'    
		},
		endDate:{
			validator : function(value, param){
				var s=$("#"+param[0]).combobox("getValue");
				if(s!= "" && value != ""){
					return s<= value; 
				}else{
					return true;
				}
			},
			message : '结束日期不能小于开始日期！'    
		},
		same:{
			validator : function(value, param){
				if($("#"+param[0]).val() != "" && value != ""){
					return $("#"+param[0]).val() == value; 
				}else{
					return true;
				}
			},
			message : '两次输入的密码不一致！'    
		},
		notsame:{
			validator : function(value, param){
				if($("#"+param[0]).val() != "" && value != ""){
					return !($("#"+param[0]).val() == value); 
				}else{
					return true;
				}
			},
			message : '新密码和旧密码一致！'    
		},
		account: {//param的值为[]中值  
	        validator: function (value, param) {  
	            if (value.length < param[0] || value.length > param[1]) {  
	                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';  
	                return false;  
	            } else {  
	                if (!/^[\w]+$/.test(value)) {  
	                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';  
	                    return false;  
	                } else {  
	                    return true;  
	                }  
	            }  
	        }, message: ''
		},
		numberWithPoint:{ //校验小数后n位
	        validator : function(value,param){ 
	        	var patern="^(([1-9]{1}[0-9]*)|([0-9]+\.[0-9]{1,"+param[0]+"}))$";
	        	var hh=new RegExp(patern);
	        	if(!hh.test(value)){
	        		 $.fn.validatebox.defaults.rules.xiaoshu.message ="最多保留"+param[0]+"位小数！";
	        	}
	        	return hh.test(value);
	        },
	        message: ''
	    },
	    password:{//密码验证
	    	validator : function(value){ 
	        	var patern=/^(?![0-9]+$)(?![a-zA-Z]+$)\S{6,16}$/;
	        	var hh=new RegExp(patern);
	        	return hh.test(value);
	        },
	        message: '8-16位，不能有空格，纯数字或纯字母'
	    },
	    confirmPass: {
            validator: function(value, param){
                var pass = $(param[0]).passwordbox('getValue');
                return value == pass;
            },
            message: '确认密码与原密码不一致'
        }
	});
	
}