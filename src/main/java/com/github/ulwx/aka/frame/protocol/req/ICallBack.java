package com.github.ulwx.aka.frame.protocol.req;

import com.ulwx.tool.StringUtils;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.servlet.support.CallbackBean;
import com.github.ulwx.aka.frame.servlet.support.NotfyReqParm;

public interface ICallBack {

	public static enum CallBackType{
		//无回调
		NO_CALLBACK(0),
		// 1：异步回调
		ASYNC_CALLBACK(1),
		//2:网关回调
		GATEWAY_CALLBACK(2);


		private final int value;
		//构造方法必须是private或者默认
		private CallBackType(int value) {
			this.value = value;
		}
		// public static CallType valueOf(int value) {
		// 	return valueFrom(value);
		// }
		public static CallBackType valueFrom(int value) {
			switch (value) {
				case 0:
					return CallBackType.NO_CALLBACK;
				case 1:
					return CallBackType.ASYNC_CALLBACK;
				case 2:
					return CallBackType.GATEWAY_CALLBACK;
				default:
					return null;
			}
		}


		public static CallBackType valueFrom(String value) {
			int val=0;
			try {
				val= Integer.valueOf(StringUtils.trim(value));
			} catch (Exception e) {

			}
			return valueFrom(val);
		}
		@Override
		public String toString(){
			return value+"";
		}

		public String toName(){
			switch (value) {
				case 0:
					return "NONE";
				case 1:
					return "NOTIFY";
				case 2:
					return "CALLBACK";
				default:
					return "";
			}
		}

	}
	/**
	 *
	 * @param callBackType 1：异步回调  2:网关回调 3:本地提交回调
	 * @param reqPrm
	 * @param interLogReq
	 * @return
	 */
	public CallbackBean callBack(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq);

	/**
	 *
	 * @param callBackType 1：异步回调  2:网关回调 3:本地提交回调
	 * @param reqPrm
	 * @param interLogReq
	 * @return
	 */
	public CallbackBean callBackMore(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq);
}
