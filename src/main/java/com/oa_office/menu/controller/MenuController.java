package com.oa_office.menu.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.common.util.ExtJsonResult;
import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.menu.pojo.TreeNode;
import com.oa_office.menu.pojo.dto.MenuQueryDTO;
import com.oa_office.menu.service.IMenuChirdService;
import com.oa_office.menu.service.IMenuService;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.service.IRoleService;
import com.oa_office.user.pojo.User;

@Controller
@RequestMapping("/menu/")
public class MenuController {
	@Autowired
	private IMenuService iMenuService;
	@Autowired
	private IMenuChirdService menuChirdService;
	@Autowired
	private IRoleService iRoleService;

	// 显示指定角色的菜单
	@RequestMapping(value = "show_menuByrole", method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> showMenuById(HttpSession session) {
		// 获取登录信息
		User user = (User) session.getAttribute("user");
		Set<Role> roles = user.getRoles();
		int roleCode = 0;
		String id = "";
		for (Role role : roles) {
			if (Integer.valueOf(role.getRolecode()) >= roleCode) {
				id = role.getid();
				roleCode = Integer.valueOf(role.getRolecode());
			}
		}
		return iMenuService.findNodes(id);
	}

	// 显示所有子菜单
	@RequestMapping(value = "show_allSmenu", method = RequestMethod.GET)
	public @ResponseBody List<MenuChird> showAllSmenu() {
		return menuChirdService.findAll();
	}

	// 查看所有父类菜单
	@RequestMapping(value = "show_Pmenu", method = RequestMethod.GET)
	public @ResponseBody List<MenuQueryDTO> showParentMenu() {
		List<Menu> menus = iMenuService.findAll();
		return MenuQueryDTO.ToDTO(menus);
	}

	// 增加和修改父菜单
	@RequestMapping(value = "add_menu", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse addMenu(String iconcls, String menucode, String menuname, String menuurl,
			String menuid) {
		try {
			Menu menu = new Menu();
			menu.setid(menuid);
			menu.setIconcls(iconcls);
			menu.setmenucode(menucode);
			menu.setMenuname(menuname);
			menu.setMenuurl(menuurl);
			iMenuService.saveOrUpdate(menu);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	/// 增加和修改子菜单
	@RequestMapping(value = "add_menuChird", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse addMenu(MenuChird menuChild) {
		try {
			menuChirdService.saveOrUpdate(menuChild);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	// 给指定角色添加菜单
	@RequestMapping(value = "add_menuByrole", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse addMenuByRole(String id, String menuid) {
		try {
			if(menuid.equals("--请选择菜单--")) {
				return new ExtAjaxResponse(false, "请选择菜单!");
			}else {
				Role role = iRoleService.findOne(id);
				Menu menu = iMenuService.findByMenuName(menuid);
				role.getMenus().add(menu);
				iRoleService.saveOrUpdate(role);
				return new ExtAjaxResponse(true, "操作成功！");
			}
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
		

	}

	// 添加子菜单
	@RequestMapping(value = "add_Smenu", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse addSonMenu(String iconcls, String menucode, String menuname, String menuurl,
			String menuid) {
		try {
			Menu menu1 = iMenuService.findOne(menuid);
			MenuChird chird = new MenuChird();
			chird.setIconcls(iconcls);
			chird.setmenucode(menucode);
			chird.setMenuname(menuname);
			chird.setMenuurl(menuurl);
			menu1.getMenuChird().add(chird);
			iMenuService.saveOrUpdate(menu1);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}

	}

	// 查看子菜单
	@RequestMapping(value = "show_Smenu", method = RequestMethod.GET)
	public @ResponseBody List<MenuQueryDTO> showSonMenu(String menuid) {
		List<MenuChird> menus = menuChirdService.findSon(menuid);
		return MenuQueryDTO.sToDTO(menus);
	}

	// 删除子菜单
	@RequestMapping(value = "delete_Smenu", method = RequestMethod.GET)
	public @ResponseBody ExtAjaxResponse deleteSonMenu(String menuSid) {
		try {
			MenuChird menuChird = menuChirdService.findOne(menuSid);
			menuChird.setMenu(null);
			menuChirdService.delete(menuChird);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	// TODO 查看指定角色父类菜单
	@RequestMapping(value = "show_role_menu", method = RequestMethod.GET)
	public @ResponseBody List<MenuQueryDTO> showRoleMenu(String menuid) {
		List<MenuQueryDTO> mendto = MenuQueryDTO.ToDTO(iMenuService.findAll(iRoleService.findroleidBymenuid(menuid)));
		return mendto;
	}

	// 下拉返回角色已有菜单result
	@RequestMapping(value = "down_Hmenu")
	public @ResponseBody ExtJsonResult<MenuQueryDTO> downHmenuByrole(String id) {
		Role role = iRoleService.findOne(id);
		Set<Menu> menus = role.getMenus();
		List<Menu> list = new ArrayList<Menu>();
		for (Menu menu : menus) {
			list.add(menu);
		}
		List<MenuQueryDTO> lists = MenuQueryDTO.ToDTO(list);
		return new ExtJsonResult<>(lists);
	}

	// 删除菜单
	@RequestMapping(value = "delete_menu", method = RequestMethod.GET)
	public @ResponseBody ExtAjaxResponse deleteMenu(String menuid) {
		try {
			iMenuService.delete(iMenuService.findOne(menuid));
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	// 给指定角色删除菜单
	@RequestMapping(value = "delete_Rmenu", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse deleteRoleMenu(String id, String menuid) {
		try {
			if(menuid.equals("--请选择菜单--")) {
				return new ExtAjaxResponse(false, "请选择删除的菜单！");
			}else {
				Role role = iRoleService.findOne(id);
				Menu menu = iMenuService.findByMenuName(menuid);
				Set<Menu> menus = role.getMenus();
				Set<Menu> mMenus = new HashSet<Menu>();
				for (Menu m : menus) {
					if(!(m.getid().equals(menu.getid()))) {
						mMenus.add(m);
					}
				}
				role.setMenus(mMenus);
				iRoleService.saveOrUpdate(role);
				return new ExtAjaxResponse(true, "操作成功！");
			}
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}
	
	// 下拉返回角色未有菜单result
	@RequestMapping(value = "down_Nmenu")
	public @ResponseBody ExtJsonResult<MenuQueryDTO> downNmenuByrole(String id) {
		if("".equals(id)) {
			return null;
		}else {
			Role role = iRoleService.findOne(id);
			List<Menu> menus = iMenuService.findAll();
			Set<Menu> set = new HashSet<Menu>();
			set.addAll(menus);
			Set<Menu> menun = role.getMenus();
			List<Menu> list = new ArrayList<Menu>();
			list.addAll(set);
			for (Menu menu : set) {
				for (Menu item : menun) {
					if (menu.getid().equals(item.getid())) {
						list.remove(menu);
					}
				}
			}
			List<MenuQueryDTO> lists = MenuQueryDTO.ToDTO(list);
			return new ExtJsonResult<>(lists);
		}
	}
	// TODO 给指定角色删除菜单
	// @RequestMapping(value = "delete_Rmenu", method = RequestMethod.GET)
	// public @ResponseBody ExtAjaxResponse deleteRoleMenu(String roleid,String
	// menuid) {
	// try {
	// iMenuService.deleteRM(roleid, menuid);
	// return new ExtAjaxResponse(true, "操作成功！");
	// } catch (Exception e) {
	// return new ExtAjaxResponse(false, "操作失败！");
	// }
	// }
}
