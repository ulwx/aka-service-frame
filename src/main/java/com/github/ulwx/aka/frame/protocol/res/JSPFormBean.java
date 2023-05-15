package com.github.ulwx.aka.frame.protocol.res;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.utils.IError;
import com.github.ulwx.aka.frame.servlet.support.InterParam;

import java.util.List;

public class JSPFormBean extends IUFrameBean<JSPFormBean> {

	public Data data = new Data();

	@Comment("一个网关页面的信息")
	public static class Data {
		@Comment("接口请求信息")
		public InterParam interParm;
		@Comment("业务端传的回调URL，如果为空，则走默认回调页面")
		public String redirectUrl;
		@Comment("操作类型，由业务端定义，用于区分操作")
		public String operType = "";
		@Comment("提交的URL，如果为空字符串，则走默认的提交servlet")
		public String submitUrl="";
		@Comment("form表单页面的title")
		public String pageTitle="";
		@Comment("form表单的title")
		public String formTitle="";
		@Comment("post提交的的参数")
		public List<PostInputParam> postParam;

	}

	public static class PostInputParam {
		@Comment("输入框类型，有text,file,combox,password")
		public String inputType="text";
		@Comment("输入框对应的中文名称")
		public String chName="";
		@Comment("输入框对应的英文名称")
		public String engName="";
		@Comment("输入框对应值")
		public String value="";
		@Comment("输入框是否是hidden")
		public boolean isHidden=false;
		@Comment("验证类型，预定义的类型有：number, email,mobile,minlen[12],maxlen[20],length[10,20],len[20]。多个以逗号分隔")
		public String[] validateType=new String[0];
		@Comment("是否必须有值")
		public boolean require=false;
		@Comment("是否在顶端框里")
		public boolean inTopBox=false;
		@Comment("是否只读")
		public boolean readonly=false;

		public static class ValidateType{
			public static   String number="number";
			public static   String email="email";
			public static   String mobile="mobile";
			public static  String length(int start,int end) {
				return "length["+start+","+end+"]";
			}
			public static  String len(int len) {
				return "len["+len+"]";
			}
			public static  String minLen(int len) {
				return "minlen["+len+"]";
			}
			public static  String maxLen(int len) {
				return "maxlen["+len+"]";
			}
		}

		/**
		 *
		 * @param inputType 输入框类型，有text,file,combox,password
		 * @param chName 输入框的中文名称
		 * @param engName 输入框的英文名称
		 * @param value 默认值
		 */
		public void set(String inputType, String chName, String engName, String value) {

			this.inputType = inputType;
			this.chName = chName;
			this.engName = engName;
			this.value = value;
		}

		/**
		 * 是否是hidden类型，即处于隐藏状态
		 * @param isHidden
		 */
		public void setHidden(boolean isHidden) {
			this.isHidden = isHidden;
		}

		/**
		 * 设置验证类型，预定义的类型有：number, email,mobile,minlen[12],maxlen[20],length[10,20],len[20]。
		 * 多个以逗号分隔。
		 * @param validateType
		 */
		public void setValidateType(String[] validateType) {
			this.validateType = validateType;
		}

		/**
		 * 是否设置只读
		 * @param readonly
		 */
		public void setReadonly(boolean readonly) {
			this.readonly = readonly;
		}

		/**
		 *
		 * @param inputType 输入框类型，有text,file,combox,password
		 * @param chName 输入框的中文名称
		 * @param engName 输入框的英文名称
		 * @param value 默认值
		 */
		public PostInputParam(String inputType, String chName, String engName, String value) {
			super();
			this.inputType = inputType;
			this.chName = chName;
			this.engName = engName;
			this.value = value;
		}

		public PostInputParam() {
		}




	}
	public static JSPFormBean SUC() {
		throw new UnsupportedOperationException();
	}
	
	public static JSPFormBean SUC(String msg) {
		throw new UnsupportedOperationException();
	}
	
	
	public static <T extends  JSPFormBean> T  SUC(T bean) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(bean.getMessage())) {
			bean.setMessage("处理成功!");
		} else {
			////
		}

		bean.setStatus(1);
		bean.setError(0);
		return bean;
	}
	
	
	
	public  static JSPFormBean SUC(String msg, String redirectUrl) {
		JSPFormBean bean = new JSPFormBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);
		bean.data.redirectUrl = redirectUrl;
		return bean;//wrapRedirectUrl(bean);
	}

	public static JSPFormBean SUC(String msg, String redirectUrl, String operType) {
		JSPFormBean bean = new JSPFormBean();
		bean.setMessage(msg);
		bean.setStatus(1);
		bean.setError(0);

		bean.data.redirectUrl = redirectUrl;
		bean.data.operType = operType;
		return bean;
	}


	public static JSPFormBean ERROR(int errorCode, String errorMsg, String redirectUrl) {
		JSPFormBean bean = new JSPFormBean();
		bean.setMessage(IError.get(errorCode, errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.data.redirectUrl = redirectUrl;
		 return bean;//wrapRedirectUrl(bean);
	}
	public static JSPFormBean ERROR(String errorMsg, String redirectUrl) {
		JSPFormBean bean = new JSPFormBean();
		bean.setMessage(errorMsg);
		bean.setStatus(0);
		bean.setError(IError.COMMON_ERROR);
		bean.data.redirectUrl = redirectUrl;
		 return bean;//wrapRedirectUrl(bean);
	}
	public static JSPFormBean ERROR(int errorCode, String errorMsg, String redirectUrl, String operType) {
		JSPFormBean bean = new JSPFormBean();
		bean.setMessage(IError.get(errorCode, errorMsg));
		bean.setStatus(0);
		bean.setError(errorCode);
		bean.data.operType = operType;
		bean.data.redirectUrl = redirectUrl;
		return bean;//wrapRedirectUrl(bean);
	}
	public Data getData() {
		return data;
	}
}
