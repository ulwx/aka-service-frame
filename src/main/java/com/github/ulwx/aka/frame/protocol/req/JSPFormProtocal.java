package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.JSPFormBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.support.NotfyReqParm;
import com.github.ulwx.aka.frame.servlet.support.NotifyJspCallbackBean;

/**
 * 返回本地网关界面
 */
 public interface JSPFormProtocal extends IProtocol,ICallBack{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  JSPFormBean genBean() throws Exception;

	public  JSPFormBean getTestBean() throws Exception;

	@Override
	default NotifyJspCallbackBean callBack(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		return null;
	}

	@Override
	default NotifyJspCallbackBean callBackMore(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		return null;
	}

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.JSPForm;
	}
}
