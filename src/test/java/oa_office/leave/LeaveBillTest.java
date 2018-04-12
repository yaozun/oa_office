package oa_office.leave;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;
import com.oa_office.common.util.AJAXResultMessage;
import com.oa_office.common.util.ExtPageable;
import com.oa_office.common.util.SessionUtil;
import com.oa_office.common.util.WorkflowVariable;
import com.oa_office.leave.dto.LeaveDTO;
import com.oa_office.leave.entity.Leave;
import com.oa_office.leave.service.ILeaveService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;
import com.oa_office.user.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class LeaveBillTest {
    
	@Autowired
	private ILeaveService leaveService;
	
	@Autowired
	private IUserService userService;
	
	/*
	 * 
	 * 测试请假申请单的编写
	 */
	@Test
	@Rollback(false)
    public void saveOrUpdate() {
		    Leave leave = new Leave();
		    leave.setId(1L);
		    leave.setUserId("40287d81608cbe1d01608cbe29b10000");
		    leave.setApplyTime(new Date());
		    leave.setLeaveType("3");
		    Map<String, Object> vars = new HashMap<String, Object>();
		    vars.put("reason", "我要去旅游");
		    vars.put("memo", "请批准");
		    leave.setVariables(vars);
    		leaveService.save(leave);
    		System.out.println("申请成功");	
    }
	
	/*@RequestMapping(value = "/delete")
    public @ResponseBody AJAXResultMessage delete(Long id) {
    	try {
    		leaveService.delete(id);
    		return new AJAXResultMessage(true,"操作成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new AJAXResultMessage(false,"操作失败!");
	    }
    }*/
	
	
	/*
	 * 
	 * 测试通过用户id查找请假单
	 */
	@Test
	@Rollback(false)
    public void findLeaveByUserId() {
    	
    	ExtPageable pageable = new ExtPageable();
        pageable.setPage(1);
 
    	/*Page<Leave> page = new PageImpl<Leave>(new ArrayList<Leave>(), pageable.getPageable(), 0);
		try {
    		String userId = SessionUtil.getUserName(session);*/
    	    
    		Page page = leaveService.findLeave("40287d81608cbe1d01608cbe29b10000", pageable.getPageable());
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
    		variables.put("userName", "Mr.zeng");//请假审批人
    		User user = userService.findOne("40287d81608cbe1d01608cbe29b10000"); //请假人aa
    		String userName = user.getUserName();
    		variables.put("applyUserId",userService.findOne("40287d81608cbe1d01608cbe29b10000").getUserName() );
   		
    		leaveService.startWorkflow(1L, variables);
    }
	
	/**//**
	 * 查询待处理流程任务
	 * @param pageable	分页对象
	 * @param session	通过会话获取登录用户(请假人)
	 * @return
	 *//*
	@RequestMapping(value = "/findTasks")
    public @ResponseBody Page<LeaveDTO> findTodoTasks(HttpSession session,ExtjsPageable pageable) {
		Page<LeaveDTO> page = new PageImpl<LeaveDTO>(new ArrayList<LeaveDTO>(), pageable.getPageable(), 0);
    	try {
    		page = leaveService.findTodoTasks(SessionUtil.getUserName(session), pageable.getPageable());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    	
    	return page;
    }
	
	*//**
     * 签收任务
     *//*
    @RequestMapping(value = "claim/{id}")
    public @ResponseBody AJAXResultMessage claim(@PathVariable("id") String taskId, HttpSession session) {
    	try{
	    	leaveService.claim(taskId, SessionUtil.getUserName(session));
	    	return new AJAXResultMessage(true,"任务签收成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new AJAXResultMessage(false,"任务签收失败!");
	    }
    }
    
    *//**
     * 完成任务
     * @param id
     * @return
     *//*
    @RequestMapping(value = "complete/{id}")
    public @ResponseBody AJAXResultMessage complete(@PathVariable("id") String taskId, WorkflowVariable var) {
    	try{
    		Map<String, Object> variables = var.getVariableMap();
	    	leaveService.complete(taskId, variables);
	    	return new AJAXResultMessage(true,"任务签收成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new AJAXResultMessage(false,"任务签收失败!");
	    }
    }*/
}
