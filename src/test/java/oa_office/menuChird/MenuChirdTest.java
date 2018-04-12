package oa_office.menuChird;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.dept.pojo.Dept;
import com.oa_office.dept.service.IDeptService;
import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.menu.service.IMenuChirdService;
import com.oa_office.menu.service.IMenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class MenuChirdTest {

	@Autowired
	private IMenuChirdService menuChirdService;

	@Autowired
	private IMenuService menuService;

	// 测试添加
	@Test
	@Rollback(value = false)
	public void saveMenuChird() {
		MenuChird menuChird = new MenuChird();
		menuChird.setMenuname("测试");
		menuChird.setmenucode("test");
		menuChird.setIconcls("dd");
		menuChird.setMenuurl("dddd");
		Menu menu = menuService.findOne("2c9b8a5660e034180160e076f3aa0000");
		menuChird.setMenu(menu);
		menuChirdService.saveOrUpdate(menuChird);
	}

	// 删除子菜单测试
	@Test
	@Rollback(value = false)
	public void deleteMenuChird() {
		MenuChird menuChird = menuChirdService.findOne("2c9b388760e0700d0160e07023b80000");
		menuChird.setMenu(null);
		menuChirdService.delete(menuChird);
	}

	// 添加父菜单测试
	@Test
	@Rollback(value = false)
	public void deleteMenu() {
		Menu menu = menuService.findOne("2c9b388760e07ac30160e07ad8030000");
		menuService.delete(menu);
	}

}
