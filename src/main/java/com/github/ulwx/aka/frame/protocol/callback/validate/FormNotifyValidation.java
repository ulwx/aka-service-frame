package com.github.ulwx.aka.frame.protocol.callback.validate;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.res.JSPFormBean.PostInputParam;
import com.github.ulwx.aka.frame.protocol.utils.UlFrameSignatures;
import com.github.ulwx.aka.frame.servlet.support.ValidationException;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.StringUtils;
import com.ulwx.tool.ValidationUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FormNotifyValidation {
	private static Logger logger = Logger.getLogger(FormNotifyValidation.class);
	public FormNotifyValidation() {
		// TODO Auto-generated constructor stub
	}

	public static boolean sign(Map<String, String> signMap, String signArgStr, String namespace)throws ValidationException {

		AkaFrameProperties akaFrameProperties=BeanGet.getBean(AkaFrameProperties.class);
		String signKey=akaFrameProperties.getRequest().getSign().getRequestSignKey();
		String toSignStr = ObjectUtils.toStringUseFastJson(signMap, false, true, false);
		String signStr = UlFrameSignatures.signature(signKey, toSignStr);
		logger.debug("toSignStr="+toSignStr+",signStr="+signStr+",signArgStr="+signArgStr);
		if(!signStr.equals(signArgStr)) {
			throw new ValidationException("sign","参数一致性校验失败！");
		}
		return true;
	}

	public static boolean validate(Map<String, String[]> map,String namespace) throws ValidationException {
		// 验证框架
		Map<String, String> signMap=new TreeMap<>();
		for (String key : map.keySet()) {
			if (key.endsWith("_options")) {
				String argName = StringUtils.trimTailString(key, "_options");
				String argValue = map.get(argName)[0];
				String keyValue=map.get(key)[0]+"";
				String optionValue = "{" + map.get(key)[0] + "}";
				Map<String, Object> optMap=null;
				try {
					optMap = (Map) ObjectUtils.fromJsonToMap(optionValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new ValidationException(optionValue + "解析出错！", e);
				}
				for (String optKey : optMap.keySet()) {
					if (optKey.equals("hidden") || optKey.equals("readonly")) {
						boolean optValue = (Boolean) optMap.get(optKey);
						if (optValue) {
							signMap.put(argName, argValue);
							signMap.put(key, keyValue);
						}
					} else if (optKey.equals("required")) {
						boolean optValue = (Boolean) optMap.get(optKey);
						if (optValue && StringUtils.isEmpty(argValue)) {
							throw new ValidationException(argName, "邮箱格式输入有误");
						}
					} else if (optKey.equals("validType")) {
						List optValueList = (List) optMap.get(optKey);
						for (int i = 0; i < optValueList.size(); i++) {
							String optValue = optValueList.get(i) + "";
							// number, email,mobile,minlen[12],maxlen[20],length[10,20],len[20]
							if (optValue.equals(PostInputParam.ValidateType.email)) {
								if (!ValidationUtils.isEmail(argValue)) {
									throw new ValidationException(argName, "邮箱格式输入有误");
								}

							} else if (optValue.equals(PostInputParam.ValidateType.mobile)) {
								if (!ValidationUtils.isMobile(argValue)) {
									throw new ValidationException(argName, "手机号格式不正确！");
								}

							} else if (optValue.equals(PostInputParam.ValidateType.number)) {
								if (!ValidationUtils.isNum(argValue)) {
									throw new ValidationException(argName, "必须输入数值！");
								}

							} else {
								if (optValue.startsWith("len") || optValue.startsWith("minlen")
										|| optValue.startsWith("maxlen") || optValue.startsWith("length")) {
									optValue = StringUtils.trimTailString(optValue, "]");
									String[] strs = optValue.split("\\[");
									String a = StringUtils.trim(strs[0]);
									if (a.equals("len")) {
										int len = Integer.valueOf(strs[1]);
										if (argValue.length() != len) {
											throw new ValidationException(argName, "必须输入" + len + "个字符！");
										}
									} else if (a.equals("minlen")) {
										int len = Integer.valueOf(strs[1]);
										if (argValue.length() < len) {
											throw new ValidationException(argName, "至少输入" + len + "个字符！");
										}
									} else if (a.equals("maxlen")) {
										int len = Integer.valueOf(strs[1]);
										if (argValue.length() > len) {
											throw new ValidationException(argName, "最多只能" + len + "个字符！");
										}
									} else if (a.equals("length")) {
										String[] vls = strs[1].split(",");
										int minLen = Integer.valueOf(vls[0]);
										int maxLen = Integer.valueOf(vls[1]);
										if (argValue.length() < minLen || argValue.length() > maxLen) {
											throw new ValidationException(argName,
													"只能输入" + minLen + "到" + maxLen + "个字符！");
										}
									} else {
										throw new ValidationException(argName, "参数验证类型非法！");
									}
								}

							}

						}
					}

				}

			}
		}
		
		sign(signMap,map.get("signStr")[0],namespace);

		return true;
	}

}
