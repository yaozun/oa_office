package com.oa_office.reimburse.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.AJAXResultMessage;
import com.oa_office.common.util.ExtPageable;
import com.oa_office.common.util.SessionUtil;
import com.oa_office.common.util.WorkflowVariable;
import com.oa_office.dept.pojo.Dept;
import com.oa_office.leave.dto.LeaveDTO;
import com.oa_office.leave.entity.Leave;
import com.oa_office.leave.service.ILeaveService;
import com.oa_office.position.pojo.Position;
import com.oa_office.position.service.IPositionService;
import com.oa_office.reimburse.pojo.Reimburse;
import com.oa_office.reimburse.pojo.ReimburseDTO;
import com.oa_office.reimburse.service.IReimburseService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

@Controller
@RequestMapping(value = "/reimburse")
public class ReimburseController {
	@Autowired
	private IReimburseService reimburseService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IPositionService iPositionService;

	@Autowired
	private TaskService taskService;

	// 填写请假申请单
	@RequestMapping(value = "/saveOrUpdate")
	public @ResponseBody AJAXResultMessage saveOrUpdate(ReimburseDTO reimburseDTO, HttpSession session) {
		try {
			String userId = SessionUtil.getUserId(session);
			reimburseDTO.setApplyTime(new Date());
			reimburseDTO.setUserId(userId);
			reimburseDTO.setReimburseStaus("未审批");
			Reimburse reimburse = new Reimburse();
			// 把DTO的转化为对应的类型
			BeanUtils.copyProperties(reimburse, reimburseDTO);

			reimburseService.save(reimburse);
			return new AJAXResultMessage(true, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "操作失败!");
		}
	}

	@RequestMapping(value = "/delete")
	public @ResponseBody AJAXResultMessage delete(String id) {
		try {
			reimburseService.delete(id);
			return new AJAXResultMessage(true, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "操作失败!");
		}
	}

	@RequestMapping(value = "/reimburse-list")
	public @ResponseBody /* List<Reimburse> */ Page<Reimburse> findLeaveByUserId(HttpSession session,
			ExtPageable pageable) {
		Page<Reimburse> page = new PageImpl<Reimburse>(new ArrayList<Reimburse>(), pageable.getPageable(), 0);
		try {
			String userId = SessionUtil.getUserId(session);
			page = reimburseService.findReimburse(userId, pageable.getPageable());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/*-------------------------------------流程引擎web层------------------------------------------*/

	/**
	 * 启动流程
	 * 
	 * @param leaveId
	 *            报销信息Id
	 * @param session
	 *            通过会话获取登录用户(申请报销人)
	 * @return
	 */
	@RequestMapping(value = "/start")
	public @ResponseBody AJAXResultMessage start(@RequestParam(name = "id") String id, HttpSession session) {
		Reimburse remiburse = reimburseService.findOne(id);
		if ("未审批".equals(remiburse.getReimburseStaus())) {
			try {
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("applyUserId", SessionUtil.getUserId(session));
				reimburseService.startWorkflow(id, variables);
				return new AJAXResultMessage(true, "操作成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new AJAXResultMessage(false, "操作失败!");
			}
		} else if ("审批中...".equals(remiburse.getReimburseStaus())) {
			return new AJAXResultMessage(false, "流程审批中，请耐心等待..");
		} else {
			return new AJAXResultMessage(false, "流程已结束!");
		}
	}

	/**
	 * 查询待处理流程任务
	 * 
	 * @param pageable
	 *            分页对象
	 * @param session
	 *            通过会话获取登录用户(申请报销人)
	 * @return
	 */
	@RequestMapping(value = "/findTasks")
	public @ResponseBody Page<Reimburse> findTodoTasks(HttpSession session, ExtPageable pageable) {
		Page<Reimburse> page = new PageImpl<Reimburse>(new ArrayList<Reimburse>(), pageable.getPageable(), 0);
		try {
			page = reimburseService.findTodoTasks(SessionUtil.getUserId(session), pageable.getPageable());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 签收任务
	 */
	@RequestMapping(value = "claim/{id}")
	public @ResponseBody AJAXResultMessage claim(@PathVariable("id") String taskId, HttpSession session) {
		//Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//if (task != null) {
			try {
				reimburseService.claim(taskId, SessionUtil.getUserId(session));
				return new AJAXResultMessage(true, "任务签收成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new AJAXResultMessage(false, "任务已被其他人签收!");
			}
		//} 
	}

	/**
	 * 完成任务
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "complete/{id}")
	public @ResponseBody AJAXResultMessage complete(@PathVariable("id") String taskId, WorkflowVariable var) {
		try {
			Map<String, Object> variables = var.getVariableMap();
			// 领导不同意申请，直接结束流程
			if ("false".equals(var.getValues())) {
				String businessKey = reimburseService.getBusinessByTaskId(taskId);
				Reimburse reimburse = reimburseService.findOne(businessKey);
				reimburse.setReimburseStaus("申请不通过");
				reimburseService.save(reimburse);
				reimburseService.complete(taskId, variables);
			} else {
				Position position = iPositionService.findByPositionName("财务部经理");
				if (position != null) {
					User user = position.getUsers().toArray(new User[] {})[0];
					if (user != null) {
						variables.put("userId", user.getId());
						reimburseService.complete(taskId, variables);
						return new AJAXResultMessage(true, "任务签收成功!");
					}
				}
				return new AJAXResultMessage(false, "任务签收失败!");
			}
			return new AJAXResultMessage(true, "任务签收成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "任务签收失败!");
		}
	}
}
