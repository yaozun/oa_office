package oa_office.position;

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
import com.oa_office.position.pojo.Position;
import com.oa_office.position.service.IPositionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext.xml"
})
@Transactional
public class PositionTest {
	@Autowired
	private IPositionService positionService;
	
	@Autowired
	private IDeptService deptService;
	
	@Test//测试增加职位
	@Rollback(value=false)
	public void savePosition() {
		Dept dept = deptService.findOne("2c9b8a47607df49101607df49c110000");
		Position position = new Position();
		position.setPositionName("Java工程师");
		position.setDept(dept);
		positionService.saveOrUpdate(position);
	}
	
	@Test//测试删除职位
	@Rollback(value=false)
	public void deletePosition() {
		Position position = positionService.findOne("2c9b8a47607df13301607df14ba70000");
		positionService.delete(position);
	}
	
	@Test//测试更新职位
	@Rollback(value=false)
	public void updatePosition() {
		Position position = positionService.findOne("2c9b8a47607c393201607c393dd40000");
		position.setPositionName("Android工程师");
		positionService.saveOrUpdate(position);
	}
	
	@Test//测试查询所有职位
	@Rollback(value=false)
	public void findAllPosition() {
		List<Position> positions = positionService.findAll();
		for (Position position : positions) {
			System.out.println(position);
		}
	}
	
	//测试级联删除，使用ALL建议先移除关系（update）再delete
	@Test
	@Rollback(value=false)
	public void deleteByDept() {
		Position position = positionService.findOne("2c9b8a47607df89301607df89e630000");
		//解除与Dept之间的联系
		position.setDept(null);
		//先更新再删除
		positionService.saveOrUpdate(position);
		positionService.delete(position);
	}
}
