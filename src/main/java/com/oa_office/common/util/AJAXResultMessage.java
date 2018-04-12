package com.oa_office.common.util;

public class AJAXResultMessage 
{
	private boolean success;
	private String msg;
	
	public AJAXResultMessage(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public String getMsg() {
		return msg;
	}	
}
