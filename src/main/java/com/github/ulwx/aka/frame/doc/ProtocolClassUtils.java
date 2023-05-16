package com.github.ulwx.aka.frame.doc;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.protocol.res.NullDataRes;
import com.github.ulwx.aka.frame.utils.ModuleInfo;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.ulwx.tool.ClassUtils;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.StringUtils;
import com.ulwx.type.TInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ProtocolClassUtils {
	private static Logger log = LoggerFactory.getLogger(ProtocolClassUtils.class);
	public volatile static int MY_SPACE_NUM = 2;

	public static void getFieldDesc(Field f, FieldInfo finfo) {

		Comment[] cs = f.getAnnotationsByType(Comment.class);
		String cnName = "";
		String des = "";
		for (int m = 0; m < cs.length; m++) {
			if (!des.equals("")) {
				des = des + ";";
			}
			des = des + cs[m].value();
			if (StringUtils.hasText(cs[m].cn())) {
				cnName = cs[m].cn();
			}
		}
		finfo.name = f.getName();
		finfo.cname = cnName;
		finfo.comment = des;
		finfo.type = f.getType().getSimpleName();
	}

	public static void getClassDesc(Class<?> c, ClassInfo finfo) {

		Comment[] cs = (Comment[]) c.getAnnotationsByType(Comment.class);
		String cnName = "";
		String des = "";
		String method = "get";
		boolean done = false;
		String author = "";
		for (int m = 0; m < cs.length; m++) {
			if (!des.equals("")) {
				des = des + ";";
			}
			des = des + cs[m].value();
			if (StringUtils.hasText(cs[m].cn())) {
				cnName = cs[m].cn();
			}
			if (StringUtils.hasText(cs[m].method())) {
				method = cs[m].method();
			}
			if (cs[m].done()) {
				done = true;
			}
			if (StringUtils.hasText(cs[m].author())) {
				author = cs[m].author();
			}
		}
		finfo.name = c.getSimpleName();
		finfo.cname = cnName;
		finfo.comment = des;
		finfo.type = "Object";
		finfo.name = "";
		finfo.method = method;
		finfo.done = done;
		finfo.author = author;
	}

	public static class ClassInfo {
		public String name;// 英文名称
		public String cname;// 中文名称
		public String type;// 类型
		public String comment;// 注释
		public String method;// 请求方法
		public boolean done;// 是否完成
		public String author;// 作者

	}

	public static class FieldInfo {
		public String name;// 英文名称
		public String cname;// 中文名称
		public String type;// 类型
		public String comment;// 注释

		public String toString() {
			String ret = "";
			if (StringUtils.hasText(type)) {
				ret = ret + "" + type + ",";
			}
			if (StringUtils.hasText(cname)) {
				ret = ret + "" + cname + ",";
			}
			if (StringUtils.hasText(comment)) {
				ret = StringUtils.trimTailString(ret, ",");
				ret = ret + " ," + comment;

			}
			ret=ret.replace("<br/>",",&nbsp;");
			return StringUtils.trimTailString(ret, ",");
		}


		public String toJson(boolean isArrayOrObject, String str) {
			if (isArrayOrObject) {
				String ret = "";
				ret = "\"" + this.name + "\":" + str + " /*" + this.toString() + "*/";
				return ret;
			} else {
				return "\"" + this.name + "\": \"" + str + this.toString() + "\"";
			}
		}
	}

	public volatile static String MY_LN = "\n";

	/**
	 * 
	 * @param c
	 * @param nspace
	 *            当前每行的起始空格数
	 * @return
	 */
	public static String toJson(Class c, int nspace, String comment) {

		String LN = MY_LN;
		int SPACE_NUM = MY_SPACE_NUM;

		if (nspace == 0) {
			LN = "";
			SPACE_NUM = 0;
		}

		String ret = "{";
		TInteger len = new TInteger(0);
		Field[] fs = ObjectUtils.getFields(c, len); 
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			Type fieldGenericType = f.getGenericType();
			String fn = f.getName();// 字段名称
			Class fc = f.getType();
			String fiedStr = "";
			String typeDes = "";////
			FieldInfo finfo = new FieldInfo();
			getFieldDesc(f, finfo);// 获取cname和comment
			
			if (List.class.isAssignableFrom(fc) || fc.isArray()) { // fc为List的子类或数组
				// 获取泛型参数类型
				Class itemClass = null;
				if (List.class.isAssignableFrom(fc)) {
					itemClass = ClassUtils.getActualType(fieldGenericType);
					if (itemClass == null) {
						throw new RuntimeException(fn + "为List类型，必须为泛型声明，如List<User>!");
					}
				} else {
					itemClass = fc.getComponentType();
				}
				if (itemClass.isPrimitive() || ObjectUtils.isPrimitiveWapper(itemClass) || itemClass == String.class
						|| isOther(itemClass)) {
					typeDes = "Array(" + itemClass.getSimpleName() + ")";
					finfo.type = typeDes;

					ret = ret + LN + NSpace(nspace + SPACE_NUM) + finfo.toJson(true, "");
					ret = ret + LN + NSpace(nspace + SPACE_NUM) + "[";
					ret = ret + LN + NSpace(nspace + SPACE_NUM) + "]";
					ret = ret + ",";

				} else {
					typeDes = "Array(Object)";

					finfo.type = typeDes;

					ret = ret + LN + NSpace(nspace + SPACE_NUM) + finfo.toJson(true, "");
					ret = ret + LN + NSpace(nspace + SPACE_NUM) + "[";
					ret = ret + LN + NSpace(nspace + SPACE_NUM * 2) + toJson(itemClass, nspace + SPACE_NUM * 2, "");
					ret = StringUtils.trimTailString(ret, ",");
					ret = ret + LN + NSpace(nspace + SPACE_NUM) + "]";
					ret = ret + ",";

				}

			} else if (ObjectUtils.isPrimitiveWapper(fc) || fc.isPrimitive() || fc == String.class || isOther(fc)) {
				typeDes = "" + fc.getSimpleName() + "";
				finfo.type = typeDes;
				ret = ret + LN + NSpace(nspace + SPACE_NUM) + finfo.toJson(false, "");
				ret = ret + ",";
			} else {
				typeDes = "Object";
				finfo.type = typeDes;
				ret = ret + LN + NSpace(nspace + SPACE_NUM) + finfo.toJson(true, "");
				ret = ret + LN + NSpace(nspace + SPACE_NUM) + toJson(fc, nspace + SPACE_NUM, finfo.toString());
				ret = ret + ",";
			}
		} // for
		ret = StringUtils.trimTailString(ret, ",");
		ret = ret + LN + NSpace(nspace) + "}";
		return ret;
	}

	public static String NSpace(int n) {
		return StringUtils.repeat(" ", n);
	}

	public static boolean isOther(Class<?> c) {
		if (c == Date.class || c == LocalDateTime.class || c == LocalDate.class || c == LocalTime.class
				|| c == File.class ||c==Logger.class||c==Protocol.class ) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		

	}

	public static String getResPrococolClassName(String propertyKey,String v, String protocolBH, String moduleName,String reqClassName) {
		// inf/v1/teach/xyz  com.yscf.trade.web.protocol.v1.trade.req

		String reqClassNamePrefix=reqClassName.substring(0,reqClassName.lastIndexOf("."));
		String reqClassNamesuffix=reqClassName.substring(reqClassName.lastIndexOf(".")+1);
		String innerResClassName=reqClassNamePrefix+"."+reqClassNamesuffix+"$$"+reqClassNamesuffix;
		try {
			Class.forName(innerResClassName);
			return innerResClassName;

		} catch (Exception e) {
			try {
				innerResClassName=reqClassNamePrefix+"."+reqClassNamesuffix+"$Res"+reqClassNamesuffix;
				Class.forName(innerResClassName);
				return innerResClassName;
			} catch (Exception exception) {
				return NullDataRes.class.getName();
			}

		}
	}

	public static VersionModules[] getProtocolBH(String namespace) {

		AkaFrameProperties akaFrameProperties=BeanGet.getBean(AkaFrameProperties.class);
		String PROTOCOL_REQ_PACKAGE=akaFrameProperties.getProtocols().stream().
				filter(p-> p.getNamesapce().equals(namespace))
				.findFirst().get().getPackageName();
		UIFrameAppConfig uiFrameAppConfig=BeanGet.getBean(UIFrameAppConfig.class);
		List<Protocol> classPathRootResources= uiFrameAppConfig.getNamespaceToProtocols()
				.get(namespace);
		classPathRootResources=classPathRootResources.stream().
				sorted(Comparator.comparing(p -> p.getClass().getName())).collect(Collectors.toList());
		// 查找模块
		Map<String,VersionModules> versionModuleMap=new HashMap<>();
		for (int k=0; k<classPathRootResources.size(); k++) {
			//com.yscf.trade.web.protocol.v1.mod1.UserAdd
			Protocol  protocol=classPathRootResources.get(k);
			String packagePath=protocol.getClass().getPackage().getName();
			String verModName=StringUtils.trimLeadingString(packagePath,PROTOCOL_REQ_PACKAGE).substring(1);
			String[] strs=verModName.split("\\.");
			String key=namespace+":"+strs[0];
			VersionModules versionModules=versionModuleMap.get(key);
			if(versionModules==null){
				versionModules = new VersionModules();
				versionModuleMap.put(key,versionModules);
			}

			versionModules.setNamespace(namespace);
			versionModules.setVersion(strs[0]);

			List<ModuleInfo> moduleList = versionModules.getModuleList();
			ModuleInfo moduleInfo = new ModuleInfo();
			moduleInfo.setModuleName(strs[1]);
			moduleInfo.setVersion(strs[0]);
			moduleInfo.setNamespace(namespace);
			boolean find=false;
			for(int i=0;i<moduleList.size(); i++){
				ModuleInfo elem=moduleList.get(i);
				if(elem.equals(moduleInfo)){
					moduleInfo=elem;
					find=true;
					break;
				}
			}
			if(!find){
				moduleList.add(moduleInfo);
			}

			List<ProtocolClassInfo> classList = moduleInfo.getClassList();
			ProtocolClassInfo pi = new ProtocolClassInfo();
			pi.setModName(moduleInfo.getModuleName());

			pi.setName(strs[2]);
			pi.setClassName(protocol.getClass().getName());
			pi.setVer(versionModules.getVersion());
			pi.setNamespace(namespace);
			classList.add(pi);

		}

		return versionModuleMap.values().toArray(new VersionModules[0]);
	}
	


}
