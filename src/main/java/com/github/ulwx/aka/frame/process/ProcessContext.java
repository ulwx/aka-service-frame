package com.github.ulwx.aka.frame.process;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.RequestUtils;


public class ProcessContext {

	private Map<String,String[]> reqArgs=new TreeMap<String,String[]>();
	private Map<String,Object[]> generatedArgs=new HashMap<String,Object[]>();

	public Map<String, String[]> getReqArgs() {
		return reqArgs;
	}
	public void setReqArgs(Map<String, String[]> reqArgs) {
		this.reqArgs = reqArgs;
	}
	public Map<String, Object[]> getGeneratedArgs() {
		return generatedArgs;
	}
	public void setGenArgs(Map<String, Object[]> generatedArgs) {
		this.generatedArgs = generatedArgs;
	}
	
	public Object getFromGenArgs(String key){
		Object[] val=generatedArgs.get(key);
		if(ArrayUtils.isEmpty(val)){
			return "";
		}else{
			return val[0];
		}
	}
	
	
	
	public String getStringFromReqArgs(String key){
		String[] val=reqArgs.get(key);
		if(ArrayUtils.isEmpty(val)){
			return "";
		}else{
			return val[0];
		}
	}
	
	public void putToReqArgs(String key,String val){
	
		reqArgs.put(key, new String[]{val});
	}
	public void putMapToReqArgs(Map<String,String[]> map){
		reqArgs.putAll(map);
	}
	public void putToReqArgs(String key,String[] val){
		reqArgs.put(key, val);
	}
	public void putToGenArgs(String key,Object val){
		Object array=Array.newInstance(val.getClass(), 1);
		Array.set(array, 0, val);
		generatedArgs.put(key, (Object[])array);
	}
	
	public void putToGenArgs(String key,Object[] val){
		generatedArgs.put(key, val);
	}
	public void putMapToGenArgs(Map<String,Object[]> map){
		generatedArgs.putAll(map);
	}
	
	public void putToGenArgsAppend(String key,Object val){
		Object[] objs=generatedArgs.get(key);
		if(objs!=null && objs.length>0){
			objs=(Object[])ArrayUtils.insert(objs, objs.length, val);
			generatedArgs.put(key, objs);
		}else{
			Object array=Array.newInstance(val.getClass(), 1);
			Array.set(array, 0, val);
			generatedArgs.put(key, (Object[])array);
		}
	}
	
	public RequestUtils getRequest(){
		Map<String,Object[]> newMap=new HashMap<>();
		newMap.putAll(reqArgs);
		RequestUtils ru=new RequestUtils(newMap);
		ru.getrParms().putAll(generatedArgs);

		return ru;
	}
	
}
