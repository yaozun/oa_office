package com.oa_office.position.pojo.dto;

public class PositionDTO {

	// 职位主键
	private String id;
	// 职位名称
	private String positionName;
	// 部门名称
	private String dept;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}

}
