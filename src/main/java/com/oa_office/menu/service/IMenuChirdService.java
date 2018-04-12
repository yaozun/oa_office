package com.oa_office.menu.service;

import java.util.List;

import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.menu.pojo.TreeNode;

public interface IMenuChirdService {
	public void saveOrUpdate(MenuChird menu);
	public void delete(MenuChird menu);
	public void delete(List<MenuChird> menus);
	public List<MenuChird> findAll(List<String> ids);
	public MenuChird findOne(String id);
	public List<MenuChird> findAll();
	public List<MenuChird> findSon(String menuid);
}
