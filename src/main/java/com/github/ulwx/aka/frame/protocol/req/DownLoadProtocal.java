package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.DownLoadBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;

/**
 * 返回下载的文件流
 */
 public interface DownLoadProtocal extends IProtocol{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  DownLoadBean genBean() throws Exception;

	public  DownLoadBean getTestBean() throws Exception;

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.DownLoad;
	}
}
