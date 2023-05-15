package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.STRBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 返回自定义String
 */
 public interface STRProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  STRBean genBean() throws Exception;

	public  STRBean getTestBean() throws Exception;

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.STR;
	}
}
