package com.github.ulwx.aka.frame.process;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.ulwx.tool.ArrayUtils;
import com.ulwx.tool.RequestUtils;


public class ProcessContext  {

	private RequestUtils ru=new RequestUtils();

	public void putAll(Map<String, ? extends Object[]> map) {
		ru.putAll(map);
	}

	public void setString(String name, String value) {
		ru.setString(name, value);
	}

	public void setValue(String name, Object value) {
		ru.setValue(name, value);
	}

	public void setValues(String name, Object[] values) {
		ru.setValues(name, values);
	}

	public void setStrings(String name, String[] values) {
		ru.setStrings(name, values);
	}

	public Map<String, Object[]> getrParms() {
		return ru.getrParms();
	}

	public void setrParms(Map<String, Object[]> rParms) {
		ru.setrParms(rParms);
	}

	public void setInt(String name, Integer value) {
		ru.setInt(name, value);
	}

	public void setFloat(String name, Float value) {
		ru.setFloat(name, value);
	}

	public void setLong(String name, Long value) {
		ru.setLong(name, value);
	}

	public void setDouble(String name, Double value) {
		ru.setDouble(name, value);
	}

	public void setLocalTime(String name, LocalTime value) {
		ru.setLocalTime(name, value);
	}

	public void setLocalDateTime(String name, LocalDateTime value) {
		ru.setLocalDateTime(name, value);
	}

	public void setLocalDate(String name, LocalDate value) {
		ru.setLocalDate(name, value);
	}

	public void setLocalTimes(String name, LocalTime[] value) {
		ru.setLocalTimes(name, value);
	}

	public void setLocalDateTimes(String name, LocalDateTime[] value) {
		ru.setLocalDateTimes(name, value);
	}

	public void setLocalDates(String name, LocalDate[] value) {
		ru.setLocalDates(name, value);
	}

	public void setObject(String name, Object value) {
		ru.setObject(name, value);
	}

	public void setObjects(String name, Object[] values) {
		ru.setObjects(name, values);
	}

	public Object getObject(String name) {
		return ru.getObject(name);
	}

	public Object[] getObjects(String name) {
		return ru.getObjects(name);
	}

	public String getString(String name) {
		return ru.getString(name);
	}

	public Object getValue(String name) {
		return ru.getValue(name);
	}

	public Object[] getValues(String name) {
		return ru.getValues(name);
	}

	public String getTrimString(String name) {
		return ru.getTrimString(name);
	}

	public File getFile(String name) {
		return ru.getFile(name);
	}

	public void setBody(String bodyStr) {
		ru.setBody(bodyStr);
	}

	public <T> T getJson(String name, Class<T> c) throws Exception {
		return ru.getJson(name, c);
	}

	public String getBody() {
		return ru.getBody();
	}

	public <T> T getBody(Class<T> c) {
		return ru.getBody(c);
	}

	public <T> T getJson(String name, Type t) throws Exception {
		return ru.getJson(name, t);
	}

	public <T> T getBody(Type t) throws Exception {
		return ru.getBody(t);
	}

	public String getFileContentType(String name) {
		return ru.getFileContentType(name);
	}

	public String[] getFileContentTypes(String name) {
		return ru.getFileContentTypes(name);
	}

	public String getFileName(String name) {
		return ru.getFileName(name);
	}

	public String[] getFileNames(String name) {
		return ru.getFileNames(name);
	}

	public File[] getFiles(String name) {
		return ru.getFiles(name);
	}

	public <T> T getJavaBean(T javabean) {
		return ru.getJavaBean(javabean);
	}

	public Integer getInt(String name) {
		return ru.getInt(name);
	}

	public Float getFloat(String name) {
		return ru.getFloat(name);
	}

	public Double getDouble(String name) {
		return ru.getDouble(name);
	}

	public Long getLong(String name) {
		return ru.getLong(name);
	}

	public Byte getByte(String name) {
		return ru.getByte(name);
	}

	public Boolean getBoolean(String name) {
		return ru.getBoolean(name);
	}

	public String[] getStrings(String name) {
		return ru.getStrings(name);
	}

	public void setFloats(String name, Float[] values) {
		ru.setFloats(name, values);
	}

	public void setDoubles(String name, Double[] values) {
		ru.setDoubles(name, values);
	}

	public void setLongs(String name, Long[] values) {
		ru.setLongs(name, values);
	}

	public void setInts(String name, Integer[] values) {
		ru.setInts(name, values);
	}

	public void setBoolean(String name, Boolean value) {
		ru.setBoolean(name, value);
	}

	public void setFile(String name, File file, String fileName, String contentType) {
		ru.setFile(name, file, fileName, contentType);
	}

	public void setFiles(String name, File[] files, String[] fileNames, String[] contentTypes) {
		ru.setFiles(name, files, fileNames, contentTypes);
	}

	public void setBooleans(String name, Boolean[] values) {
		ru.setBooleans(name, values);
	}

	public void setBytes(String name, Byte[] values) {
		ru.setBytes(name, values);
	}

	public void setByte(String name, Byte value) {
		ru.setByte(name, value);
	}

	public Date getDayDate(String name) {
		return ru.getDayDate(name);
	}

	public LocalDate getLocalDate(String name) {
		return ru.getLocalDate(name);
	}

	public LocalTime getLocalTime(String name) {
		return ru.getLocalTime(name);
	}

	public LocalDateTime getLocalDateTime(String name) {
		return ru.getLocalDateTime(name);
	}

	public Date[] getDayDates(String name) {
		return ru.getDayDates(name);
	}

	public LocalDate[] getLocalDates(String name) {
		return ru.getLocalDates(name);
	}

	public LocalTime[] getLocalTimes(String name) {
		return ru.getLocalTimes(name);
	}

	public LocalDateTime[] getLocalDateTimes(String name) {
		return ru.getLocalDateTimes(name);
	}

	public void setWholeDate(String name, Date value) {
		ru.setWholeDate(name, value);
	}

	public void setWholeDates(String name, Date[] values) {
		ru.setWholeDates(name, values);
	}

	public Date getWholeDate(String name) {
		return ru.getWholeDate(name);
	}

	public Date[] getWholeDates(String name) {
		return ru.getWholeDates(name);
	}

	public Integer[] getInts(String name) {
		return ru.getInts(name);
	}

	public Long[] getLongs(String name) {
		return ru.getLongs(name);
	}

	public Boolean[] getBooleans(String name) {
		return ru.getBooleans(name);
	}

	public Byte[] getBytes(String name) {
		return ru.getBytes(name);
	}

	public Double[] getDoubles(String name) {
		return ru.getDoubles(name);
	}

	public Float[] getFloats(String name) {
		return ru.getFloats(name);
	}

	public RequestUtils getRu() {
		return ru;
	}

	public void setRu(RequestUtils ru) {
		this.ru = ru;
	}
}
