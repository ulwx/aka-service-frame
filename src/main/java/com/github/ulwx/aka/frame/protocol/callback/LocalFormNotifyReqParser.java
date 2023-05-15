package com.github.ulwx.aka.frame.protocol.callback;

import com.github.ulwx.aka.frame.protocol.callback.validate.FormNotifyValidation;
import com.github.ulwx.aka.frame.servlet.support.NotfyReqParm;
import com.github.ulwx.aka.frame.servlet.support.NotifyReqParser;
import com.github.ulwx.aka.frame.servlet.support.ReqCodeParm;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class LocalFormNotifyReqParser extends NotifyReqParser {

	public LocalFormNotifyReqParser() { 
		// TODO Auto-generated constructor stub
	}



	@Override
	public NotfyReqParm parseRequest(HttpServletRequest request, ReqCodeParm reqCodeParm) throws Exception {
		// TODO Auto-generated method stub
		NotfyReqParm parm = new NotfyReqParm();
		Map<String, String[]> map = request.getParameterMap();
		
		FormNotifyValidation.validate(map,reqCodeParm.getNamespace());
		Map<String, Object[]> newMap=new HashMap<>();
		newMap.putAll(map);
		RequestUtils ru = new RequestUtils(newMap);
		

		String reponseStr = ObjectUtils.toStringUseFastJson(map);

		String merchantno = "";
	
		parm.setPlatformNo(merchantno);
		parm.setResponseString(reponseStr);
		String requestNo = (String) ru.getString("requestno");
		parm.setRequestNo(requestNo);

		String userId = (String) ru.getString("userId");
		parm.setPlatformUserNo(userId);

		String status = "";
		String errorCode = "";
		String errorMessage = "";
		parm.setStatus(status);
		parm.setErrorCode(errorCode);
		parm.setErrorMessage(errorMessage);
		Map<String, Object> retMap = new HashMap<>();

		for (String key : map.keySet()) {
			String[] val = map.get(key);
			if (val != null && val.length > 0) {
				retMap.put(key, val[0]);
			}
		}
		parm.setRequestMap(retMap);

		parm.setPlatformNo("jyd");
		return parm;
	}

	public static void main(String[] args) {

	}

}
