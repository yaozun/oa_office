package com.oa_office.role.service;

import java.util.List;
import java.util.Set;

import com.oa_office.role.pojo.Role;


public interface IRoleService {
	public void saveOrUpdate(Role role);
	public void delete(Role role);
	public void delete(List<Role> roles);
	public List<Role> findAll(List<String> ids);
	public Role findOne(String id);
	public List<Role> findAll();
	public List<String> findroleidBymenuid(String menuid);
	public List<String> findmenuByroleid(String roleid);
}
