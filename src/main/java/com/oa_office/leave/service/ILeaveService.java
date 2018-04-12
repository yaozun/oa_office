package com.oa_office.leave.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oa_office.leave.dto.LeaveDTO;
import com.oa_office.leave.entity.Leave;
import com.oa_office.user.pojo.User;


public interface ILeaveService 
{
	//请假业务
	public void save(Leave leave);
	public void delete(Long id);
	public Leave findOne(Long id);
	
	public Page<Leave> findLeave(String userId,Pageable pageable);
	
	//请假业务:高级查询(可扩展)
	
	//流程业务
	//1.启动流程
	public ProcessInstance startWorkflow(Long leaveId, Map<String, Object> variables);//findOne(Long id);
	//2.查询流程任务
	public Page<LeaveDTO> findTodoTasks(String userId, Pageable pageable);
	
	//3.签收流程任务
	public void claim(String taskId,String userId);
	
	//4.完成流程任务
	public void complete(String taskId, Map<String, Object> variables);
        
	//5.结束(终止)流程实例
	
	//6.查询运行中的流程
	public Page<LeaveDTO> findRunningProcessInstaces(Page<Leave> page, int[] pageParams);
	//7.查询已结束的流程
	public Page<LeaveDTO> findFinishedProcessInstaces(Page<Leave> page, int[] pageParams);
	//8.查询流程定义对象
	//protected ProcessDefinition getProcessDefinition(String processDefinitionId);
	//9.设置请假单的实时状态
	//public List<Leave> setLeaveStatus(List<Leave> leaves);
	
	//查找审批领导
	public List<String> findDeptLeaders(String applyUserId);
	
	//通过当前任务id查找对应请假单的id
	public String getBusinessByTaskId(String taskId);
}
