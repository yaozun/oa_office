package com.oa_office.common.util;

public class ExtAjaxResponse 
{
	private boolean success= true;
	private String msg= "";
	
	public ExtAjaxResponse() {}
	
	public ExtAjaxResponse(boolean success) {
		this.success = success;
	}
	
	public ExtAjaxResponse(boolean success,String msg) {
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
