package com.github.ulwx.aka.frame.protocol.req;

import com.github.ulwx.aka.frame.protocol.res.JSONBean;
import com.github.ulwx.aka.frame.protocol.services.domain.InterLogReqBean;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.servlet.support.NotfyReqParm;
import com.github.ulwx.aka.frame.servlet.support.NotifyStrCallbackBean;

/**
 * 返回JSON接口
 */
 public interface JSONProtocal extends IProtocol,ICallBack{
	/**
	 * 业务方法，必须覆盖此方法实现业务
	 * @return
	 * @throws Exception
	 */
	public  JSONBean genBean() throws Exception;

	public  JSONBean getTestBean() throws Exception;
	
	default  public NotifyStrCallbackBean callBack(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		  throw new UnsupportedOperationException();
	}

	default  public NotifyStrCallbackBean callBackMore(CallBackType callBackType, NotfyReqParm reqPrm, InterLogReqBean interLogReq) {
		  throw new UnsupportedOperationException();
	}

	@Override
	default String getInterType() {
		return UiFrameConstants.InterType.JSON;
	}
}
