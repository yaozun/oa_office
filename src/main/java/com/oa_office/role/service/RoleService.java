package com.oa_office.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.role.dao.RoleDao;
import com.oa_office.role.pojo.Role;
@Service
@Transactional
public class RoleService implements IRoleService{
	@Autowired
	private RoleDao roledao;

	@Override
	public void saveOrUpdate(Role role) {
		roledao.save(role);
		
	}

	@Override
	public void delete(Role role) {
		roledao.delete(role);
		
	}

	@Override
	public void delete(List<Role> roles) {
		roledao.delete(roles);
		
	}

	@Override
	public List<Role> findAll(List<String> ids) {
		// TODO Auto-generated method stub
		return (List<Role>) roledao.findAll(ids);
	}

	@Override
	public Role findOne(String id) {
		// TODO Auto-generated method stub
		return roledao.findOne(id);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roledao.findAll();
	}

	@Override
	public List<String> findroleidBymenuid(String menuid) {
		// TODO Auto-generated method stub
		return roledao.findRoleid(menuid);
	}
	public List<String> findmenuByroleid(String roleid){
		return roledao.findMenuid(roleid);		
	}
}
