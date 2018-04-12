package com.oa_office.leave.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import com.oa_office.common.util.AJAXResultMessage;
import com.oa_office.common.util.SessionUtil;
import com.oa_office.dept.pojo.Dept;
import com.oa_office.leave.dao.LeaveRepository;
import com.oa_office.leave.dto.LeaveDTO;
import com.oa_office.leave.entity.Leave;
import com.oa_office.position.pojo.Position;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

@Service
@Transactional
public class LeaveService implements ILeaveService {


	/**
	 * 系统服务
	 */
	@Autowired
	private LeaveRepository leaveRepository;

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

	/*----------------------------------------------系统业务--------------------------------------------*/
	@Override
	public void save(Leave leave) {
		leaveRepository.save(leave);
	}

	@Override
	public void delete(Long id) {
		Leave leave = leaveRepository.findOne(id);
		if (leave != null) {
			leaveRepository.delete(leave);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Leave findOne(Long id) {
		return leaveRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Leave> findLeave(String userId, Pageable pageable) {
		return leaveRepository.findLeave(userId, pageable);
	}

	@Override
	/*
	 * public List<Leave> setLeaveStatus(List<Leave> leaves) { List arrayList = new
	 * ArrayList<>(leaves); for (int i = 0; i < arrayList.size(); i++) { Leave leave
	 * = (Leave) arrayList.get(i); if (!"未审批".equals(leave.getLeaveStaus())) {
	 * 
	 * String processInstanceId = leave.getProcessInstanceId(); ProcessInstance
	 * processInstance = runtimeService.createProcessInstanceQuery()
	 * .processInstanceId(processInstanceId).active().singleResult(); if
	 * (processInstance != null) { leave.setLeaveStaus("审批中..."); } else {
	 * if(!"申请不通过".equals(leave.getLeaveStaus())) { leave.setLeaveStaus("审批完成"); } }
	 * arrayList.set(i, leave); } } return arrayList; }
	 */

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

	/*----------------------------------------------流程业务--------------------------------------------*/
	@Override
	public ProcessInstance startWorkflow(Long leaveId, Map<String, Object> variables) {
		// 1.声明流程实例
		ProcessInstance processInstance = null;
		// 2.获取创建好的请假实例
		Leave leave = leaveRepository.findOne(leaveId);
		if (leave != null) {
			try {
				// 3.授权
				identityService.setAuthenticatedUserId(leave.getUserId());
				// 4.把entityid转换为业务主键(用于工作流程实例关联业务)
				String businessKey = leave.getId().toString();
				// 5.启动流程实例:processDefinitionKey, businessKey, variables
				processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
				// 6.业务实体与流程实例关联
				leave.setProcessInstanceId(processInstance.getId());
				leave.setApplyTime(new Date());
				// 6.1 by su
				leave.setLeaveStaus("审批中...");
				// 7.更新到数据库
				leaveRepository.save(leave);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 8.取消授权
				identityService.setAuthenticatedUserId(null);
			}
		}
		return processInstance;
	}

	@Override
	public Page<LeaveDTO> findTodoTasks(String userId, Pageable pageable) {

		List<LeaveDTO> results = new ArrayList<LeaveDTO>();
		// 根据当前人的ID查询
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
		List<Task> tasks = taskQuery.list();
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().singleResult();
			String type = processInstance.getProcessDefinitionKey();
			if ("leave".equals(type)) {
				String businessKey = processInstance.getBusinessKey();
				if (businessKey == null) {
					continue;
				}
				Leave leave = leaveRepository.findOne(new Long(businessKey));
				if (leave != null) {
					LeaveDTO leaveDTO = new LeaveDTO();
					BeanUtils.copyProperties(leave, leaveDTO);
					leaveDTO.setTaskId(task.getId());
					leaveDTO.setTaskName(task.getName());
					leaveDTO.setTaskCreateTime(task.getCreateTime());
					leaveDTO.setAssignee(task.getAssignee());
					leaveDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
					leaveDTO.setSuspended(processInstance.isSuspended());
					ProcessDefinition processDefinition = getProcessDefinition(
							processInstance.getProcessDefinitionId());
					leaveDTO.setProcessDefinitionId(processDefinition.getId());
					leaveDTO.setVersion(processDefinition.getVersion());
					results.add(leaveDTO);
				}
			}
		}
		return new PageImpl<LeaveDTO>(results, pageable, results.size());
	}

	@Override
	public Page<LeaveDTO> findRunningProcessInstaces(Page<Leave> page, int[] pageParams) {
		// repositoryService
		return null;
	}

	@Override
	public Page<LeaveDTO> findFinishedProcessInstaces(Page<Leave> page, int[] pageParams) {
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
			Leave leave = findOne(new Long(businessKey));
			if (!"申请不通过".equals(leave.getLeaveStaus())) {
				leave.setLeaveStaus("审批完成");
				save(leave);
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
