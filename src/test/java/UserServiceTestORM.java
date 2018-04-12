/*import com.oa_office.user.pojo.User;
import com.oa_office.user.pojo.dto.UserQueryDTO;
import com.oa_office.user.service.IUserService;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext.xml"

})
@Transactional
public class UserServiceTestORM
{
	@Autowired
	private IUserService userService;


	//@Test
	@Rollback(value=false)
	public void save() {
      for (int i = 1;i<2;i++){
		  User user = new User();
		  user.setUserName("Guest"+i);
		  user.setPassword("123");
		  //user.setBirthday(new Date());
		  userService.saveOrUpdate(user);
	  }
	}
	//@Test
	public void select(){
     //User user =  userService.findById((long) 39);
		System.out.println(user);
	}
	//简单分页
	//@Test
	public void page(){
		int page = 0;
		int size = 10;
		//Pageable pageable = new PageRequest(page, size);
		Pageable pageable = new PageRequest(page, size,new Sort(Sort.Direction.DESC, "id"));
		//Pageable pageable = new PageRequest(page, size, Direction.DESC,  "id","userName");
		//Pageable pageable = new PageRequest(page, size,new Sort(Direction.DESC, "id").and(new Sort(Direction.ASC, "userName")));

		Page<User> userPage = userService.findAll("%Guest%", pageable);
		System.out.println("Number:"+userPage.getNumber());
		System.out.println("NumberOfElements:"+userPage.getNumberOfElements());
		System.out.println("Size:"+userPage.getSize());
		System.out.println("TotalElements:"+userPage.getTotalElements());
		System.out.println("TotalPages:"+userPage.getTotalPages());
		System.out.println("Sort:"+userPage.getSort());

		//System.out.println("Content:"+userPage.getContent());
		for (User u : userPage.getContent()) {
			System.out.println(u);
		}

//		Page<User> userPage2 = new PageImpl<User>(content);
//		Page<User> userPage2 = new PageImpl<User>(content,pageable,total);
	}

	//@Test
	public void findBySpecification() {
		int page = 0;
		int size = 25;
		Pageable pageable = new PageRequest(page, size,new Sort(Sort.Direction.ASC, "id"));

		UserQueryDTO userQueryDTO = new UserQueryDTO();
		userQueryDTO.setUserName("Guest1");
		userQueryDTO.setPassword("123");

		Page<User> userPage =  userService.findAll(UserQueryDTO.getSpecification(userQueryDTO), pageable);

		for (User u : userPage.getContent()) {
			System.out.println(u);
		}
	}
	//@Test
	public void  findUser2(){
		User user = userService.findUser2("Guest1","123");
		System.out.println(user);
	}

}
*/