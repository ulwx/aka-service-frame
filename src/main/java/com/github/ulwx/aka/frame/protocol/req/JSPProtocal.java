package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.JSPBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 返回第三方网关界面
 */
public interface JSPProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  JSPBean genBean() throws Exception;

	public  JSPBean getTestBean() throws Exception;


	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.JSPPost;
	}
}
