package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.JSONBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 返回JSON接口
 */
 public interface JSONProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  JSONBean genBean() throws Exception;

	public  JSONBean getTestBean() throws Exception;
	


	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.JSON;
	}
}
