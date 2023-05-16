package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.req.CallBackProtocal;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.*;
import com.github.ulwx.aka.frame.annotation.Validate;
import com.github.ulwx.aka.frame.annotation.Validate.ValidateType;
import com.github.ulwx.aka.frame.protocol.req.IProtocol;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.req.ReqType;
import com.github.ulwx.aka.frame.protocol.res.BaseRes;
import com.github.ulwx.aka.frame.protocol.res.BaseResBean;
import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
import com.github.ulwx.aka.frame.protocol.utils.UiFrameConstants;
import com.github.ulwx.aka.frame.services.service.InterLogService;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * 交换操作类
 * 
 * @author 黄杰
 * 
 */
public class Exchange {

	private static Logger logger = Logger.getLogger(Exchange.class);

	/**
	 * 根据协议编号获取协议类
	 * 
	 * @param Protocol
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public static Protocol geProtocal(String namespace,
									  String Protocal,
									  String ver,
									  String modlue) throws Exception {
		try {
			if (StringUtils.hasText(Protocal)) {
				if (StringUtils.hasText(ver)) {
					if (ver.startsWith("v")) {
						//
					} else {
						ver = "v" + ver;
					}
				} else {
					ver = "v1";
				}
				UIFrameAppConfig uiFrameAppConfig=
						BeanGet.getBean(UIFrameAppConfig.class,ReqContext.getHttpServletRequest());
				AkaFrameProperties.ProtocolProperties protocolInfo=uiFrameAppConfig.getProtocolInfo(namespace);
				String className = protocolInfo.getPackageName()+ "." + ver + "." + modlue + "." + Protocal;
				Class cls = Class.forName(className);
				return (Protocol) cls.newInstance();
			}
		} catch (ClassNotFoundException ex) {
			throw new ExchangeException(404, "接口不存在！");
		} catch (Exception e) {
			logger.error("获取对象出错！", e);
			throw new ExchangeException(402, "获取接口出错！");
		}
		return null;
	}

	/**
	 * 注入请求参数
	 * 
	 * @param pro
	 * @param ru
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void injectRequestParmater(Protocol pro, RequestUtils ru) throws Exception {

		@SuppressWarnings("rawtypes")
		Class c = pro.getClass();

		Field[] curFields = c.getFields();

		if (curFields != null) {
			for (int i = 0; i < curFields.length; i++) {

				Field field = curFields[i];
				field.setAccessible(false);// 只运行public的属性

				int mod = field.getModifiers();
				if (Modifier.isPublic(mod) && !Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
					Class<?> filedClass = field.getType();
					String fieldName = field.getName();

					Set<String> keys = ru.getrParms().keySet();
					if (!keys.contains(fieldName)) {
						continue;
					}

					if (filedClass == Integer.class || filedClass == int.class) {
						Integer value = ru.getInt(fieldName);
						field.set(pro, value);
					} else if (filedClass == Byte.class || filedClass == byte.class) {
						Integer value = ru.getInt(fieldName);
						field.set(pro, value);
					} else if (filedClass == Float.class || filedClass == float.class) {
						Float value = ru.getFloat(fieldName);
						field.set(pro, value);
					} else if (filedClass == Boolean.class || filedClass == boolean.class) {
						Boolean value = ru.getBoolean(fieldName);
						field.set(pro, value);
					} else if (filedClass == Double.class || filedClass == double.class) {
						Double value = ru.getDouble(fieldName);
						field.set(pro, value);
					} else if (filedClass == Short.class || filedClass == short.class) {
						Integer value = ru.getInt(fieldName);
						field.set(pro, value);
					} else if (filedClass == Long.class || filedClass == Long.class) {
						Long value = ru.getLong(fieldName);
						field.set(pro, value);
					} else if (filedClass == String.class) {
						String value = ru.getString(fieldName);
						field.set(pro, value);
					} else if (filedClass == LocalDate.class) {
						LocalDate value = ru.getLocalDate(fieldName);
						field.set(pro, value);
					} else if (filedClass == LocalDateTime.class) {
						LocalDateTime value = ru.getLocalDateTime(fieldName);
						field.set(pro, value);
					} else if (filedClass == LocalTime.class) {
						LocalTime value = ru.getLocalTime(fieldName);
						field.set(pro, value);
					} else if (filedClass == File.class) {
						File value = ru.getFile(fieldName);
						field.set(pro, value);
					} else if (ReqType.JsonType.class.isAssignableFrom(filedClass)) {// 如果是json类型
						String strvalue = ru.getString(fieldName);
						Object val = ObjectUtils.fromJsonToObject(strvalue, filedClass);
						field.set(pro, val);
					} else if (filedClass.isArray()) {

						Class ct = filedClass.getComponentType();
						if (ct == Integer.class || ct == int.class) {
							Integer[] value = ru.getInts(fieldName);
							if (ct == int.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						}
						if (ct == Boolean.class || ct == boolean.class) {
							Boolean[] value = ru.getBooleans(fieldName);
							if (ct == boolean.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == Byte.class || ct == byte.class) {
							Byte[] value = ru.getBytes(fieldName);
							if (ct == byte.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == Float.class || ct == float.class) {
							Float[] value = ru.getFloats(fieldName);
							if (ct == float.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == Double.class || ct == double.class) {
							Double[] value = ru.getDoubles(fieldName);
							if (ct == double.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == Short.class || ct == short.class) {
							Integer[] value = ru.getInts(fieldName);
							if (ct == short.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == Long.class || ct == Long.class) {
							Long[] value = ru.getLongs(fieldName);
							if (ct == Long.class) {
								field.set(pro, ArrayUtils.toPrimitiveArray(value));
							} else {
								field.set(pro, value);
							}
						} else if (ct == String.class) {
							String valStr = ru.getString(fieldName);
							if (valStr != null && valStr.startsWith("[")) {
								try {
									Object val = ObjectUtils.fromJsonToObject(valStr, filedClass);
									field.set(pro, val);
								} catch (Exception e) {
									logger.error("", e);
									field.set(pro, null);
								}
							} else {
								String[] value = ru.getStrings(fieldName);
								field.set(pro, value);
							}
						} else if (ct == LocalDate.class) {
							LocalDate[] value = ru.getLocalDates(fieldName);
							field.set(pro, value);
						} else if (ct == LocalDateTime.class) {
							LocalDateTime[] value = ru.getLocalDateTimes(fieldName);
							field.set(pro, value);
						} else if (ct == LocalTime.class) {
							LocalTime[] value = ru.getLocalTimes(fieldName);
							field.set(pro, value);
						} else if (ct == File.class) {
							File[] value = ru.getFiles(fieldName);
							field.set(pro, value);
						} else if (ReqType.JsonType.class.isAssignableFrom(ct)) {// 如果是json类型
							String strvalue = ru.getString(fieldName);
							Object val = null;
							try {
								val = ObjectUtils.fromJsonToObject(strvalue, ct);
							} catch (Exception e) {
								logger.error("", e);
							}

							field.set(pro, val);
						} else {
							//
						}

					} else {
						//
					}

				}
			}
		}
		// 注入RequestUtils
		pro.setRequest(ru);

	}

	public static void main(String[] args) throws Exception {

		String valStr = "[10.233,2,4]";
		Type typeOfT = new com.google.gson.reflect.TypeToken<List<String>>() {
		}.getType();
		List<?> valueList = (List) ObjectUtils.fromJsonToObject(valStr, typeOfT);

		// System.out.println(((Integer)22.2));
		System.out.println(ObjectUtils.toString(valueList));

	}

	public static BaseRes validate(Protocol pro) {
		// 验证时间戳的有效性，10分钟
		Long timestamp = pro.timestamp;
		if (timestamp == null || timestamp == 0) {
			return BaseResBean.ERROR("接口请求失败，请求时间戳为空！");
		}
		LocalDateTime lt = CTime.fromLongMillsecs(timestamp);
		long minutes = CTime.dateTimediffMinutes(lt, LocalDateTime.now());
		if (minutes > 10) {
			return BaseResBean.ERROR("接口请求超时！");
		}
		return null;
	}

	public static Protocol geProtocal(RequestUtils ru) throws Exception {
		String protocalId = ru.getString(UiFrameConstants.PROTOCOL_REQ_PARM_BN);
		if (StringUtils.hasText(protocalId)) {

			String modName = ru.getString(UiFrameConstants.PROTOCOL_REQ_PARM_MOD_NAME);
			String ver = ru.getString(UiFrameConstants.PROTOCOL_REQ_PARM_VER);
			// 得到协议处理类的Class对象
			String namespace = ru.getString(UiFrameConstants.PROTOCOL_REQ_NAME_SPACE);

			Protocol pro = Exchange.geProtocal(namespace, protocalId, ver, modName);
			return pro;
		}

		return null;
	}

	public static BaseRes runBean(RequestUtils ru) {

		BaseRes javaBean = null;
		try {
			ReqContext.setRequestUtis(ru);
			Protocol pro = Exchange.geProtocal(ru);
			if (pro == null) {
				javaBean = BaseResBean.ERROR("参数非法【协议号不存在】");
				return javaBean;
			} else {
				/////
			}
			ru.setObject(UiFrameConstants.PROTOCOL_REQ_OBJ, pro);
			// 总验证

			if (javaBean == null) {
				// 注入参数
				Exchange.injectRequestParmater(pro, ru);
				javaBean = validate(pro);
				if (javaBean != null) {
					return javaBean;
				}

				// 业务协议类前置验证
				String val = pro.validate();

				if (val == null) {
					// 进行协议参数验证
					javaBean = validateParmater(pro);
					if (javaBean == null) {
						Integer test = ru.getInt(UiFrameConstants.PROTOCOL_REQ_PARM_TEST);

						if (test != null && test == 1) {// 表明是测试,走TestBean方法
							javaBean = ((IProtocol) pro).getTestBean();

						} else {
							long start = System.currentTimeMillis();
							long interLogId = 0;
							try {
								interLogId = BeanGet.getBean(InterLogService.class).insertLog(pro);//InterLogService.instance.insertLog(pro);

							} catch (Exception e) {
								logger.error("" + e, e);
								javaBean = BaseResBean.ERROR("重复请求【" + e.getMessage() + "】" + ObjectUtils.toString(pro));
								//处理重复请求
								if(pro instanceof CallBackProtocal){

								}
								return javaBean;

							}
							// 执行核心业务逻辑
							javaBean = ((IProtocol) pro).genBean();

							if (interLogId > 0) {
								BeanGet.getBean(InterLogService.class).updateLog(interLogId, javaBean, start);
							}

						}

					}

				} else {
					javaBean = BaseResBean.ERROR("参数非法【" + val + "】");
				}

			}

		} catch (ExchangeException ee) {
			logger.error(ee + "", ee);
			javaBean = BaseResBean.ERROR(ee);
		} catch (Exception ex) {
			logger.error(ex + "", ex);
			javaBean = BaseResBean.ERROR(ex.getMessage());
		} finally {
		}

		if (javaBean == null) {
			javaBean = BaseResBean.ERROR("服务器处理失败！响应返回问为空！");
		}
		return javaBean;

	}

	public static BaseRes gen(RequestUtils ru) {

		BaseRes javaBean = runBean(ru);

		if (javaBean == null) {
			javaBean = BaseResBean.ERROR("服务器处理失败！响应返回问为空！");
		}

		ru.setObject(UiFrameConstants.PROTOCOL_RES_OBJ, javaBean);

		return javaBean;

	}

	public static BaseRes validateParmater(Protocol pro) throws Exception {

		BaseRes javaBean = null;

		Class<? extends Protocol> clazz = pro.getClass();

		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			Validate[] verifys = field.getAnnotationsByType(Validate.class);
			for (Validate verify : verifys) {
				if (verify != null) {
					ValidateType[] validateTypes = verify.value();
					Object obj = field.get(pro);
					for (ValidateType validateType : validateTypes) {
						// 验证是否非空
						if (validateType == ValidateType.NOTNULL) {
							if (obj == null || StringUtils.isEmpty(obj.toString())) {
								javaBean = BaseResBean.ERROR(field.getName() + "必须有值！");
								return javaBean;
							}
						}

						// 验证类型，暂时只email，chinese类型
						if (validateType == ValidateType.EMAIL) {
							// TODO 验证属性是否为email
							if (obj != null) {
								if (obj instanceof String) {
									if (!ValidationUtils.isEmail(obj.toString())) {
										javaBean = BaseResBean.ERROR(field.getName() + "必须是email格式");
										return javaBean;
									}
								} else {
									javaBean = BaseResBean.ERROR(field.getName() + "不是字符串格式");
									return javaBean;
								}
							}

						} else if (validateType == ValidateType.CHINESE) {
							if (obj != null) {
								if (obj instanceof String) {
									if (!ValidationUtils.isChinese(obj.toString())) {
										javaBean = BaseResBean.ERROR(field.getName() + "必须为中文字符串");
										return javaBean;
									}
								} else {
									javaBean = BaseResBean.ERROR(field.getName() + "不是字符串格式");
									return javaBean;
								}

							}

						} else {

						}
						// 验证长度
						if (obj != null) {
							if (obj instanceof String) {
								String s = obj.toString();
								if (!(s.length() >= verify.minLen() && s.length() <= verify.maxLen())) {
									javaBean = BaseResBean.ERROR(field.getName() + "长度非法,合法范围为:[" + verify.minLen()
											+ " - " + verify.maxLen() + "]");

									return javaBean;
								}
							}
						}
					}
				}
			}
		}

		return javaBean;
	}

}
