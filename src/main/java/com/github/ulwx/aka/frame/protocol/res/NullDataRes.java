package com.github.ulwx.aka.frame.protocol.res;

public class NullDataRes  extends BaseRes{
	public Data data=new Data();

	public static class Data{

	}
	
	public Data getData() {
		return data;
	}
}
