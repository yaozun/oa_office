package com.oa_office.common.util;

import java.util.List;
//前端获取数据需把后台的findAll返回类型封装转换一下
public class ExtJsonResult<T> 
{
	public ExtJsonResult(List<T> result) {
		this.result = result;
	}
	private List<T> result;

	public List<T> getResult() {
		return result;
	}
	
	
}
