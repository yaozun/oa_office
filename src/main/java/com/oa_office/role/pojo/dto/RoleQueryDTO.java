package com.oa_office.role.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.oa_office.role.pojo.Role;

public class RoleQueryDTO {
	private String id;
	private String rolename;
	private String rolecode;
	
	public static List<RoleQueryDTO> roleTOdto(List<Role> roles) {
		 List<RoleQueryDTO> dtos = new  ArrayList<RoleQueryDTO>();
		for (Role item : roles) {
			RoleQueryDTO roledto = new RoleQueryDTO();
			roledto.setId(item.getid());
			roledto.setRolecode(item.getRolecode());
			roledto.setRolename(item.getRolename());
			dtos.add(roledto);
		}
		return dtos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRolecode() {
		return rolecode;
	}
	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}
	
}
