package oa_office.reimuburse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.transaction.Transactional;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oa_office.common.util.ExtPageable;
import com.oa_office.dept.service.DeptService;
import com.oa_office.dept.service.IDeptService;
import com.oa_office.leave.entity.Leave;
import com.oa_office.reimburse.pojo.ReProject;
import com.oa_office.reimburse.pojo.Reimburse;
import com.oa_office.reimburse.service.IReimburseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"

})
@Transactional // 模拟事务到这里才关闭
public class ReimburseTest {
	
	@Autowired
	private IReimburseService reimburseService;
    
	@Autowired
	private RepositoryService repositoryService;// 提供了管理和控制 流程部署和流程定义的操作。
	
	@Test
	@Rollback(value=false)
	public void reimburseRoleInsertTest() {
         
		ReProject rp1 = new ReProject();
		rp1.setName("于客户商谈");
		rp1.setPay(1000);
		
		ReProject rp2 = new ReProject();
		rp2.setName("与客户见面的车费");
		rp2.setPay(200);
		
		ReProject rp3 = new ReProject();
		rp3.setName("晚上与客户按摩费用");
		rp3.setPay(3000);
		
		Reimburse reimburse = new Reimburse();
		reimburse.setApplyTime(new Date());
		reimburse.setUserId("2c9b8a4760958a580160958a65a90001");
		reimburse.setMemo("请尽快批复");
		
		reimburse.getReProjects().add(rp1);
		reimburse.getReProjects().add(rp2);
		reimburse.getReProjects().add(rp3);
		
		double total=0;
		for(ReProject reProject : reimburse.getReProjects()) {
			total+= reProject.getPay();
	      }
		reimburse.setTotal(total);

		reimburseService.save(reimburse);
		
	}
	
	
	/**
	 * 1.部署流程
	 */
	@Test
	@Rollback(false)
	public void depoly1() {

		String fileName = "pay.bpmn";
		try {
			InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/pay.bpmn");
			// 文件的扩展名
			String extension = FilenameUtils.getExtension(fileName);
			// zip或者bar类型的文件用ZipInputStream方式部署
			DeploymentBuilder deployment = repositoryService.createDeployment();
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment.addZipInputStream(zip);
			} else {
				// 其他类型的文件直接部署 bpmn
				deployment.addInputStream(fileName, fileInputStream);
			}
			deployment.deploy();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/*
	 * 
	 * 测试请假申请单的编写
	 */
	@Test
	@Rollback(false)
    public void saveOrUpdate() {
		    Reimburse reimburse = new Reimburse();
		  
    		System.out.println("申请成功");	
    }
	
	
	/*
	 * 
	 * 测试通过用户id查找请假单
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Test
	@Rollback(false)
    public void findLeaveByUserId() {
    	
    	ExtPageable pageable = new ExtPageable();
        pageable.setPage(1);
 
    	/*Page<Leave> page = new PageImpl<Leave>(new ArrayList<Leave>(), pageable.getPageable(), 0);
		try {
    		String reimburseId = SessionUtil.getUserName(session);*/
    	    
    		Page page = reimburseService.findReimburse("2c9b8a4760958a580160958a65a90001", pageable.getPageable());
	        System.out.println("22");
    		/* } catch (Exception e) {
	       e.printStackTrace();
	    }*/
    }
	
	/*
	-------------------------------------流程引擎web层------------------------------------------
	
	*//**
	 * 启动流程（申请请假）
	 * @param leaveId	请假信息Id
	 * @param session	通过会话获取登录用户(请假人)
	 * @return
	 */
	@SuppressWarnings("unused")
	@Test
	@Rollback(false)
    public void start(/*@RequestParam(name="id") Long leaveId,HttpSession session*/) {
    	
    		Map<String, Object> variables = new HashMap<String, Object>();
    		variables.put("userId", "40287d83609bfd3e01609c0980050000");//请假审批人
//    		Reimburse reimburse = reimburseService.findOne("40287d81608cbe1d01608cbe29b10000"); //请假人aa
//    		String reimburseName = reimburse.getReimburseName();
    		variables.put("applyUserId","2c9b8a4760958a580160958a65a90001" ); //申请人
   		
    		try {
				reimburseService.startWorkflow("2c9b388760a79c240160a79c38eb0000", variables);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	

	/**
	 * 查询待处理流程任务
	 * @param pageable	分页对象
	 * @param session	通过会话获取登录用户(请假人)
	 * @return
	 */
	@Test
	@Rollback(false)
    public void findTodoTasks(/*HttpSession session,ExtjsPageable pageable*/) {
		ExtPageable pageable = new ExtPageable();
        pageable.setPage(1);
		Page<Reimburse> page = new PageImpl<Reimburse>(new ArrayList<Reimburse>(), pageable.getPageable(), 0);
    	try {
    		page = reimburseService.findTodoTasks("40287d83609bfd3e01609c0980050000", pageable.getPageable());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    
    }
	
	
	 /**
     * 完成任务
     * @param id
     * @return
     */
	@Test
	@Rollback(false)
    public void complete(/*@PathVariable("id") String taskId, WorkflowVariable var*/) {
    
    		Map<String, Object> variables = new HashMap<String,Object>();
    		variables.put("userId", "40287d83609d15d501609d273be60002");
    		variables.put("deptLeaderPass", true);
	    	reimburseService.complete("7507", variables);
	    
    }
	
	
}
