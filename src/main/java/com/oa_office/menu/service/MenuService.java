package com.oa_office.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.menu.dao.MenuChirdDao;
import com.oa_office.menu.dao.MenuDao;
import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.menu.pojo.TreeNode;
import com.oa_office.role.dao.RoleDao;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.service.IRoleService;
import com.oa_office.role.service.RoleService;
@Service
@Transactional
public class MenuService implements IMenuService{
	@Autowired
	private MenuDao menudao;
	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private MenuChirdDao menuchirddao;
	@Override
	public void saveOrUpdate(Menu menu) {
	
		menudao.save(menu);
	}

	@Override
	public void delete(Menu menu) {
		menudao.delete(menu);
		
	}

	@Override
	public void delete(List<Menu> menus) {
		menudao.delete(menus);
		
	}

	@Override
	public List<Menu> findAll(List<String> ids) {
		
		return (List<Menu>) menudao.findAll(ids);
	}

	@Override
	public Menu findOne(String id) {
	
		return menudao.findOne(id);
	}

	@Override
	public List<Menu> findAll() {
		return (List<Menu>) menudao.findAll();
	}
	
	public List<TreeNode> findNodes(String roleid){
		Role role = iRoleService.findOne(roleid);
		Set<Menu> menus = role.getMenus();
		List<Menu> menusList = new ArrayList<>();
		menusList.addAll(menus);
		sortList(menusList);
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for(Menu menu:menusList) {
			TreeNode treeNode =TreeNode.MenuToTreeNode(menu);	
			String menuid = menu.getid();
			  List<MenuChird> childrens = menuchirddao.findAllByMenuId(menuid);
			List<TreeNode> treeNodes2 = new ArrayList<TreeNode>();
			if(!childrens.isEmpty()) {
				treeNode.setExpanded(false);
				treeNode.setSelectable(false);
				treeNode.setLeaf(false);
				for(MenuChird children:childrens) {
					TreeNode node = TreeNode.MenuToTreeNode(children);		
					node.setLeaf(true);
					treeNodes2.add(node);
				}
			}
			
			treeNode.setTreeNodes(treeNodes2);
			treeNodes.add(treeNode);
		}		
		
		return treeNodes;
	}
	/**
	 * 对菜单进行排序
	 * @param menusList
	 */
	private void sortList(List<Menu> menusList) {
		Collections.sort(menusList,new Comparator<Menu>() {
			//根据菜单代号进行排序
			@Override
			public int compare(Menu o1, Menu o2) {
				if(Integer.valueOf(o1.getmenucode()).intValue() > Integer.valueOf(o2.getmenucode()).intValue()) {
					return 1;
				}else if(Integer.valueOf(o1.getmenucode()).intValue() < Integer.valueOf(o2.getmenucode()).intValue()) {
					return -1;
				}
				return 0;
			}
		});
	}

	public void deleteMenu(String menuid) {
//		List<MenuChird>  menus = menuchirddao.findAllByMenuId(menuid);
//        for(MenuChird item:menus) {
//        	menuchirddao.delete(item);
//        }       
//        menudao.deleteRMId(menuid);
//		Menu menu = menudao.findOne(menuid);	
//		
//		List<String> ids = iRoleService.findroleidBymenuid(menuid);
//      List<Role> roles = iRoleService.findAll(ids);
//		for (Role item : roles) {
//			item.getMenus().remove(menu);
//
//		}
//		menu.setRoles(new HashSet<Role>());
//		menudao.delete(menu);

						
	}

	@Override
	public Menu findByMenuName(String menuName) {
		return menudao.findByMenuName(menuName);
	}
	
//public void deleteRM(String roleid , String menuid) {
//	menudao.deleteRMId(roleid, menuid);
//}
	
}
