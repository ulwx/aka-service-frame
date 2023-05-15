package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.JSPBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.support.CallbackBean;
import com.github.ulwx.aka.frame.servlet.support.NotfyReqParm;

/**
 * 返回第三方网关界面
 */
public interface JSPProtocal extends IProtocol,ICallBack{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  JSPBean genBean() throws Exception;

	public  JSPBean getTestBean() throws Exception;

	@Override
	default CallbackBean callBack(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		return null;
	}

	@Override
	default CallbackBean callBackMore(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		return null;
	}

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.JSPPost;
	}
}
