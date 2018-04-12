package com.oa_office.reimburse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.dept.pojo.Dept;
import com.oa_office.leave.entity.Leave;
import com.oa_office.position.pojo.Position;
import com.oa_office.reimburse.dao.ReimburseRepository;
import com.oa_office.reimburse.pojo.Reimburse;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;
import com.oa_office.user.service.UserService;

@Service
@Transactional
public class ReimburseService implements IReimburseService {

	/**
	 * 系统服务
	 */
	@Autowired
	private ReimburseRepository reimburseRepository;

	/**
	 * 流程服务
	 */
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private IUserService userService;

	// ----------------------------------------------系统业务--------------------------------------------
	@Override
	public void save(Reimburse reimburse) {
		reimburseRepository.save(reimburse);
	}

	@Override
	public void delete(String id) {
		Reimburse reimburse = reimburseRepository.findOne(id);
		if (reimburse != null) {
			reimburseRepository.delete(reimburse);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Reimburse findOne(String id) {
		return reimburseRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Reimburse> findReimburse(String userId, Pageable pageable) {
		return reimburseRepository.findReimburse(userId, pageable);
	}
	
	public List<String> findDeptLeaders(String applyUserId) {
		User user = userService.findOne(applyUserId);
		Position position = user.getPosition();
		Dept dept = position.getDept();
		Set<Position> positions = dept.getPositions();
		Position manager = null;
		for (Position temp : positions) {
			if (temp.getPositionName().contains("经理")) {
				manager = temp;
				break;
			}
		}
		List<String> leaders = new ArrayList<String>();
		if (manager != null) {
			Set<User> users = manager.getUsers();
			for (User user1 : users) {
				leaders.add(user1.getId());
			}
		}
		return leaders;
	}
	
	/*public boolean isDeptLeader(String applyUserId) {
		User user = userService.findOne(applyUserId);
		Position position = user.getPosition();
		boolean isLeader = false;
		if(position.getPositionName().contains("经理")) {
			 return isLeader=true;
		}
		return isLeader;
	}*/


	// ----------------------------------------------流程业务--------------------------------------------
	@Override
	public ProcessInstance startWorkflow(String reimburseId, Map<String, Object> variables) throws Exception {
		// 1.声明流程实例
		ProcessInstance processInstance = null;
		// 2.获取创建好的报销实例
		Reimburse reimburse = reimburseRepository.findOne(reimburseId);
		if (reimburse != null) {
			try {

				// 3.授权
				identityService.setAuthenticatedUserId(reimburse.getUserId());
				// 4.把entityid转换为业务主键(用于工作流程实例关联业务)
				String businessKey = reimburse.getId();
				// 5.启动流程实例:processDefinitionKey, businessKey, variables
				processInstance = runtimeService.startProcessInstanceByKey("pay", businessKey, variables);
				// 6.业务实体与流程实例关联
				reimburse.setProcessInstanceId(processInstance.getId());
				reimburse.setApplyTime(new Date());
				// 6.1 by su
				reimburse.setReimburseStaus("审批中...");
				// 7.更新到数据库
				reimburseRepository.save(reimburse);

			} catch (Exception e) {
				throw e;
			} finally {
				// 8.取消授权
				identityService.setAuthenticatedUserId(null);
			}
		}
		return processInstance;
	}

	@Override
	public Page<Reimburse> findTodoTasks(String userId, Pageable pageable) {

		List<Reimburse> results = new ArrayList<Reimburse>();
		// 根据当前人的ID查询
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
		List<Task> tasks = taskQuery.list();
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().singleResult();
			String type = processInstance.getProcessDefinitionKey();
			if ("pay".equals(type)) {
				// 报销单的业务单号
				String businessKey = processInstance.getBusinessKey();
				if (businessKey == null) {
					continue;
				}
				Reimburse reimburse = reimburseRepository.findOne(businessKey);
				if (reimburse != null) {
					reimburse.setTaskId(task.getId());
					reimburse.setTaskName(task.getName());
					reimburse.setTaskCreateTime(task.getCreateTime());
					reimburse.setAssignee(task.getAssignee());
					reimburse.setTaskDefinitionKey(task.getTaskDefinitionKey());
					reimburse.setSuspended(processInstance.isSuspended());
					ProcessDefinition processDefinition = getProcessDefinition(
							processInstance.getProcessDefinitionId());
					reimburse.setProcessDefinitionId(processDefinition.getId());
					reimburse.setVersion(processDefinition.getVersion());

					results.add(reimburse);
				}
			}
		}
		// results List进行排序
		// 封装为Page返回
		return new PageImpl<Reimburse>(results, pageable, results.size());
	}

	@Override
	public Page<Reimburse> findRunningProcessInstaces(Page<Reimburse> page, int[] pageParams) {
		// repositoryService
		return null;
	}

	@Override
	public Page<Reimburse> findFinishedProcessInstaces(Page<Reimburse> page, int[] pageParams) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询流程定义对象
	 *
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	/**
	 * 签收流程任务
	 *
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            签收人用户ID
	 * @return
	 */
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	/**
	 * 完成流程任务
	 *
	 * @param taskId
	 *            任务ID
	 * @param variables
	 *            流程变量
	 * @return
	 */
	public void complete(String taskId, Map<String, Object> variables) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String businessKey = getBusinessByTaskId(taskId);
		taskService.complete(taskId, variables);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).active().singleResult();
		if (processInstance == null) {
			Reimburse reimburse = findOne(businessKey);
			if (!"申请不通过".equals(reimburse.getReimburseStaus())) {
				reimburse.setReimburseStaus("审批完成");
				save(reimburse);
			}
		}
	}

	/*
	 * 通过任务id，获取请假单的主键
	 * 
	 */
	public String getBusinessByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).active().singleResult();
		String businessKey = processInstance.getBusinessKey();
		return businessKey;
	}
}
