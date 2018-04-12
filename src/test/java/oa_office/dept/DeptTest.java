package oa_office.dept;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext.xml"
})
@Transactional
public class DeptTest {

	@Autowired
	private IDeptService deptService;
	
	//测试添加
	@Test
	@Rollback(value=false)
	public void saveDept() {
		Dept dept = new Dept();
		dept.setDeptName("硬件中心");
		deptService.saveOrUpdate(dept);
	}
	
	//测试删除
	@Test
	@Rollback(value=false)
	public void deleteDept() {
		Dept dept = deptService.findOne("2c9b8a47607df2d401607df2df2a0000");
		deptService.delete(dept);
	}
	
	//测试更新
	@Test
	@Rollback(value=false)
	public void updateDept() {
		Dept dept = deptService.findOne("2c9b8a47607df2d401607df2df2a0000");
		dept.setDeptName("硬件中心");
		deptService.saveOrUpdate(dept);
	}
	
	//测试查询所有
	@Test
	@Rollback(value=false)
	public void findAllDept() {
		List<Dept> lists = deptService.findAll();
		for (Dept dept : lists) {
			System.out.println(dept.getDeptName()+"===================");
		}
	}
}
