package com.github.ulwx.aka.frame.utils;

import com.github.ulwx.aka.frame.doc.ProtocolClassInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleInfo {

	private String moduleName;
	private String version;
	private String namespace;
	
	private List<ProtocolClassInfo> classList=new ArrayList<>();

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<ProtocolClassInfo> getClassList() {
		return classList;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ModuleInfo that = (ModuleInfo) o;
		return moduleName.equals(that.moduleName) && version.equals(that.version) &&
				namespace.equals(that.namespace) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(moduleName, version, namespace);
	}
}
