package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.protocol.res.CallbackBean;

public interface  CallBackProtocal extends IProtocol {
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public CallbackBean genBean() throws Exception;

	public CallbackBean genBeanMore()  throws Exception;

	public  CallbackBean getTestBean() throws Exception;

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.CallBack;
	}
}
