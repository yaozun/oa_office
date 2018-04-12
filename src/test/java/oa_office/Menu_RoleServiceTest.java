package oa_office;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.dto.MenuQueryDTO;
import com.oa_office.menu.service.IMenuService;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.service.IRoleService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext.xml"
})
@Transactional
public class Menu_RoleServiceTest {
	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private IMenuService iMenuService;
	
	//
	@Test
	public void finde() {
		String roleid="2c9cdea2607d11f201607d1202050000";
	Role role = iRoleService.findOne(roleid);
	Set<Menu> menus =role.getMenus();
	System.out.println(menus);
//	List<MenuQueryDTO> dto = MenuQueryDTO.ToDTO(menus);
	}
	//查找菜单
	//@Test//增加角色和修改角色
	public void findmenu() {
		System.out.println(iMenuService.findAll());
	}
	//@Test//增加角色和修改角色
		@Rollback(value=false)
		public void saverole() {
		Role role = new Role();
		//role.setRoleid("2c9cdea260798810016079881e320000");
		role.setRolecode("oa-3");
		role.setRolename("普通用户");
		iRoleService.saveOrUpdate(role);
		}
	//@Test//删除角色角色
	@Rollback(value=false)
	public void deleteOneRole() {
	Role role = iRoleService.findOne("2c9cdea260798810016079881e320000");
	iRoleService.delete(role);
	}
    @Test//添加菜单
	@Rollback(value=false)
	public void savemenu() {
		Menu menu = new Menu();
		//menu.setMenuid("2c9cdea260798810016079881e320000");
		menu.setmenucode("14");
		menu.setMenuname("用户管理");
		menu.setIconcls("x-fa fa-desktop");
		menu.setMenuurl("position/findAll");
		iMenuService.saveOrUpdate(menu);
	}
	//@Test//删除菜单
	@Rollback(value=false)
	public void deleteMenu() {
		Menu menu = iMenuService.findOne("2c9cdea2607c43a501607c4484f00000");
		Set<Role> roles = menu.getRoles();
		for(Role item:roles) {
			item.getMenus().remove(menu);
		}
		menu.getRoles().clear();
		iMenuService.delete(menu);
	}
	//@Test//删除某个角色的某个菜单
	@Rollback(value=false)
	public void deleteMenuByRole() {
		Role role = iRoleService.findOne("2c9cdea260798810016079881e320000");
		Menu menu = iMenuService.findOne("2c9cdea2607990ea01607990f72b0000");
		menu.getRoles().remove(role);
		role.getMenus().remove(menu);
//		Set<Role> roles = menu.getRoles();
//		Iterator<Role> it = roles.iterator();  
//		while (it.hasNext()) {  
//			it.next().setMenus(new HashSet<>());  
//		} 
		iRoleService.saveOrUpdate(role);
	}
	//@Test//给指定增加菜单
	@Rollback(value=false)
	public void savemenuByRole() {
		Role role = iRoleService.findOne("2c9cdea260798810016079881e320000");
		Menu menu = iMenuService.findOne("2c9cdea2607c43a501607c4484f00000");
		Set<Menu> menus = new HashSet<>();
		Set<Role> roles = new HashSet<>();
		menus.add(menu);
		roles.add(role);
		role.setMenus(menus);
		menu.setRoles(roles);
		iRoleService.saveOrUpdate(role);
		iMenuService.saveOrUpdate(menu);
	}
	
	@Test
	@Rollback(value=false)
	public void saveAllMenuAndRole() {
		/*//普通员工:个人中心、外出、报餐、请假、加班、日程管理
		Role role1 = iRoleService.findOne("2c9b8a47608215d901608215e66f0002");
		Set<Menu> menus = new HashSet<>();
		Menu menu1 = iMenuService.findOne("2c9b8a4760814a900160814aa0180000");
		Menu menu2 = iMenuService.findOne("2c9b8a47608230d601608230e2f50000");
		Menu menu3 = iMenuService.findOne("2c9b8a476082313701608231446d0000");
		Menu menu4 = iMenuService.findOne("2c9b8a476082319e01608231ab500000");
		Menu menu5 = iMenuService.findOne("2c9b8a47608231f101608231fe530000");
		Menu menu6 = iMenuService.findOne("2c9b8a476082329801608232a2ca0000");
		menus.add(menu1);
		menus.add(menu2);
		menus.add(menu3);
		menus.add(menu4);
		menus.add(menu5);
		menus.add(menu6);
		role1.setMenus(menus);
		iRoleService.saveOrUpdate(role1);*/
		/*//管理员:个人中心、外出、报餐、请假、加班、日程管理、通告管理、用户管理、工资管理、部门、职位
		Role role1 = iRoleService.findOne("2c9b8a47608215d901608215e5c70000");
		Set<Menu> menus = new HashSet<>();
		Menu menu1 = iMenuService.findOne("2c9b8a4760814a900160814aa0180000");
		Menu menu2 = iMenuService.findOne("2c9b8a47608230d601608230e2f50000");
		Menu menu3 = iMenuService.findOne("2c9b8a476082313701608231446d0000");
		Menu menu4 = iMenuService.findOne("2c9b8a476082319e01608231ab500000");
		Menu menu5 = iMenuService.findOne("2c9b8a47608231f101608231fe530000");
		Menu menu6 = iMenuService.findOne("2c9b8a476082329801608232a2ca0000");
		
		Menu menu7 = iMenuService.findOne("2c9b8a47608232e501608232f1860000");
		Menu menu8 = iMenuService.findOne("2c9b8a47608242390160824246540000");
		Menu menu9 = iMenuService.findOne("2c9b8a4760822faf0160822fbb640000");
		Menu menu10 = iMenuService.findOne("2c9b8a4760822e160160822e22ce0000");
		Menu menu11 = iMenuService.findOne("2c9b8a4760814b180160814b26610000");
		menus.add(menu1);
		menus.add(menu2);
		menus.add(menu3);
		menus.add(menu4);
		menus.add(menu5);
		menus.add(menu6);
		menus.add(menu7);
		menus.add(menu8);
		menus.add(menu9);
		menus.add(menu10);
		menus.add(menu11);
		role1.setMenus(menus);
		iRoleService.saveOrUpdate(role1);*/
		//超级管理员:个人中心、外出、报餐、请假、加班、日程管理、角色管理、菜单管理
		Role role1 = iRoleService.findOne("2c9b8a47608215d901608215e66d0001");
		Set<Menu> menus = new HashSet<>();
		Menu menu1 = iMenuService.findOne("2c9b8a4760814a900160814aa0180000");
		Menu menu2 = iMenuService.findOne("2c9b8a47608230d601608230e2f50000");
		Menu menu3 = iMenuService.findOne("2c9b8a476082313701608231446d0000");
		Menu menu4 = iMenuService.findOne("2c9b8a476082319e01608231ab500000");
		Menu menu5 = iMenuService.findOne("2c9b8a47608231f101608231fe530000");
		Menu menu6 = iMenuService.findOne("2c9b8a476082329801608232a2ca0000");
		//角色、菜单
		Menu menu7 = iMenuService.findOne("2c9b8a476082339701608233a2d30000");
		Menu menu8 = iMenuService.findOne("2c9b8a47608233e401608233efb70000");
		menus.add(menu1);
		menus.add(menu2);
		menus.add(menu3);
		menus.add(menu4);
		menus.add(menu5);
		menus.add(menu6);
		menus.add(menu7);
		menus.add(menu8);
		role1.setMenus(menus);
		iRoleService.saveOrUpdate(role1);
	}
	

}
