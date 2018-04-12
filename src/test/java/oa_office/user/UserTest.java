package oa_office.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.position.pojo.Position;
import com.oa_office.position.service.IPositionService;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.service.IRoleService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"

})
@Transactional // 模拟事务到这里才关闭
public class UserTest {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPositionService positionService;

	@Test
	@Rollback(false) // 默认是测试事务会关闭，不插入数据库
	public void RoleInsertTest() {

		Role role1 = new Role();
		role1.setRolename("管理员");
		role1.setRolecode("1");
		roleService.saveOrUpdate(role1);
		
		Role role2 = new Role();
		role2.setRolename("超级管理员");
		role2.setRolecode("3");
		roleService.saveOrUpdate(role2);

		Role role3 = new Role();
		role3.setRolename("普通职员");
		role3.setRolecode("0");
		roleService.saveOrUpdate(role3);


	}
	
	@Test
	@Rollback(false)
	public void addUser() {
		for (int i = 0; i < 25; i++) {
			User user = new User();
			user.setUserName("user"+i);
			user.setPassword("111");
			user.setRealName("user"+i);
			user.setGender("男");
			user.setAge(20);
			user.setPhone("1234567891"+i);
			user.setEmail("45132132@qq.com");
			user.setPayment(6000);
			user.setBirhthday(new Date());
			user.setHiredate(new Date());
			user.setImageUrl("");
			Role role = roleService.findOne("2c9b8a47609589510160958973880002");
			user.getRoles().add(role);
			Position position = positionService.findOne("2c9b8a476095dafd016095db09c30000");
			user.setPosition(position);
			userService.saveOrUpdate(user);
		}
	}

	@Test
	@Rollback(false)
	public void userRoleInsertTest() {

		/*User user = new User();
		user.setUserName("Mr.zeng");
		user.setPassword("111");
		user.setRealName("zeng");
		
		User user1 = new User();
		user1.setUserName("Mr.aa");
		user1.setPassword("222");
		user1.setRealName("aa");


		List<Role> roles = roleService.findAll();
		Role role1 = roles.get(0);
		Role role2 = roles.get(1);
		Role role3 = roles.get(2);

		HashSet<Role> roles1 = new HashSet<Role>();
		roles1.add(role1);
		roles1.add(role2);
		user1.setRoles(roles1);
		userService.saveOrUpdate(user1);

		HashSet<Role> roles2 = new HashSet<Role>();
		roles2.add(role1);
		roles2.add(role3);
		user.setRoles(roles2);
		userService.saveOrUpdate(user);*/
		
		User user = userService.findOne("2c9b8a47609c747201609c76ea920001");
		Role role = roleService.findOne("2c9b8a47609589510160958973880002");
		user.getRoles().add(role);
		userService.saveOrUpdate(user);
	}

	@Test
	@Rollback(false)
	public void userRoleDeleteTest() {

		User user = userService.findOne("40287d83607c391e01607c3928250000");
		user.setRoles(null);
		userService.delete(user);
	}

	@Test
	@Rollback(false)
	public void updateUserRoleTest() {

		List<Role> roles = roleService.findAll();
		Role role1 = roles.get(0);
		Role role2 = roles.get(1);
		Role role3 = roles.get(2);

		User user = userService.findOne("40287d83607c391e01607c39284a0001");
		user.setRoles(null);
		userService.saveOrUpdate(user);

		HashSet<Role> roles1 = new HashSet<Role>();
		roles1.add(role2);
		user.setRoles(roles1);
		userService.saveOrUpdate(user);
	}

	@Test
	@Rollback(false)
	public void findByUserNameAndPass() {
             User user = new User();
             user.setUserName("Mr.zeng");
             user.setPassword("111");
             User user1 = userService.findByUserNameAndPass(user);
             System.out.println(user1);
             Set<Role> roles = user1.getRoles();
             int roleCode = 0;
             String id = "";
             for (Role role : roles) {
			     if(Integer.valueOf(role.getRolecode()) >= roleCode) {
			    	 id = role.getid();
			    	 roleCode = Integer.valueOf(role.getRolecode());
			     }
             }
             
             if(id != null) {
            	 Role role = roleService.findOne(id);
            	 Set<Menu> menus = role.getMenus();
            	 menus.getClass();
             }
             
	}
}










