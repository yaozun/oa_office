package com.oa_office.menu.service;

import java.util.List;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.TreeNode;

public interface IMenuService {
	public void saveOrUpdate(Menu menu);
	public void delete(Menu menu);
	public void delete(List<Menu> menus);
	public List<Menu> findAll(List<String> ids);
	public Menu findOne(String id);
	public List<Menu> findAll();
	public List<TreeNode> findNodes(String roleid);
	public void deleteMenu(String menuid);
//	public void deleteRM(String roleid , String menuid);
	public Menu findByMenuName(String menuName);
}
