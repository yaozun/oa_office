package com.oa_office.reimburse.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oa_office.reimburse.pojo.Reimburse;

public interface IReimburseService {
	// 请假业务
	public void save(Reimburse reimburse);

	public void delete(String id);

	public Reimburse findOne(String id);

	public Page<Reimburse> findReimburse(String userId, Pageable pageable);

	// 请假业务:高级查询(可扩展)

	// 流程业务
	// 1.启动流程
	public ProcessInstance startWorkflow(String reimburseId, Map<String, Object> variables) throws Exception;// findOne(Long id);
	// 2.查询流程任务

	public Page<Reimburse> findTodoTasks(String userId, Pageable pageable);

	// 3.签收流程任务
	public void claim(String taskId, String userId);

	// 4.完成流程任务
	public void complete(String taskId, Map<String, Object> variables);

	// 5.结束(终止)流程实例

	// 6.查询运行中的流程
	public Page<Reimburse> findRunningProcessInstaces(Page<Reimburse> page, int[] pageParams);

	// 7.查询已结束的流程
	public Page<Reimburse> findFinishedProcessInstaces(Page<Reimburse> page, int[] pageParams);
   
	//查找当前申请人的部门领导
	public List<String> findDeptLeaders(String applyUserId);
	// 8.查询流程定义对象
	// protected ProcessDefinition getProcessDefinition(String processDefinitionId);
	// 9.设置请假单的实时状态
	//public List<Reimburse> setReimburseStatus(List<Reimburse> reimburses);
	
	//通过当前任务id查找报销单的主键
	public String getBusinessByTaskId(String taskId);
	
	//判断当前申请人是否是部门经理
	//public boolean isDeptLeader(String applyUserId);

}
