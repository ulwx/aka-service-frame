package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.ForwardBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 转发
 */
 public interface ForwardProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  ForwardBean genBean() throws Exception;

	public  ForwardBean getTestBean() throws Exception;

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.Forward;
	}
}
