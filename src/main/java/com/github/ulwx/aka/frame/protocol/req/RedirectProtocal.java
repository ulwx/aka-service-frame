package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.RedirectBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 重定向
 */
 public interface RedirectProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  RedirectBean genBean() throws Exception;

	public  RedirectBean getTestBean() throws Exception;

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.Redirect;
	}
}
