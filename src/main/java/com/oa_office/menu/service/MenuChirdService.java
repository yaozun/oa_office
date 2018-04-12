package com.oa_office.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.menu.dao.MenuChirdDao;
import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.menu.pojo.TreeNode;
@Service
@Transactional
public class MenuChirdService implements IMenuChirdService{
	@Autowired
	private MenuChirdDao menudao;
	@Autowired
	private MenuChirdDao menuchirddao;

	@Override
	public void saveOrUpdate(MenuChird menu) {
	
		menudao.save(menu);
	}

	@Override
	public void delete(MenuChird menu) {
		menudao.delete(menu);
		
	}

	@Override
	public void delete(List<MenuChird> menus) {
		menudao.delete(menus);
		
	}

	@Override
	public List<MenuChird> findAll(List<String> ids) {
		
		return (List<MenuChird>) menudao.findAll(ids);
	}

	@Override
	public MenuChird findOne(String id) {
	
		return menudao.findOne(id);
	}

	@Override
	public List<MenuChird> findAll() {
		return (List<MenuChird>) menudao.findAll();
	}
	
	public List<MenuChird> findSon(String menuid){
		return menuchirddao.findAllByMenuId(menuid);
	}


}
