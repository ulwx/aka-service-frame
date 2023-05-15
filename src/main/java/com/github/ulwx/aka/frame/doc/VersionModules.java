package com.github.ulwx.aka.frame.doc;

import com.github.ulwx.aka.frame.utils.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

public class VersionModules {

	private String version;
	private String namespace;
	
	private List<ModuleInfo> moduleList=new ArrayList<>();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ModuleInfo> getModuleList() {
		return moduleList;
	}


	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
