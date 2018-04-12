package com.oa_office.leave.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
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
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

@Controller
@RequestMapping(value = "/leave")
public class LeaveController {
	@Autowired
	private ILeaveService leaveService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IPositionService iPositionService;

	// 填写请假申请单
	@RequestMapping(value = "/saveOrUpdate")
	public @ResponseBody AJAXResultMessage saveOrUpdate(Leave leave, HttpSession session) {
		try {
			String userId = SessionUtil.getUserId(session);
			leave.setUserId(userId);
			leave.setLeaveStaus("未审批");
			leaveService.save(leave);
			return new AJAXResultMessage(true, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "操作失败!");
		}
	}

	@RequestMapping(value = "/delete")
	public @ResponseBody AJAXResultMessage delete(Long id) {
		try {
			leaveService.delete(id);
			return new AJAXResultMessage(true, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "操作失败!");
		}
	}

	@RequestMapping(value = "/leave-list")
	public @ResponseBody Page<Leave> findLeaveByUserId(HttpSession session, ExtPageable pageable) {
		Page<Leave> page = new PageImpl<Leave>(new ArrayList<Leave>(), pageable.getPageable(), 0);
		try {
			String userId = SessionUtil.getUserId(session);
			page = leaveService.findLeave(userId, pageable.getPageable());
			/* leaveService.setLeaveStatus(page.getContent()); */
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
	 *            请假信息Id
	 * @param session
	 *            通过会话获取登录用户(请假人)
	 * @return
	 */
	@RequestMapping(value = "/start")
	public @ResponseBody AJAXResultMessage start(@RequestParam(name = "id") Long leaveId, HttpSession session) {
		Leave leave = leaveService.findOne(new Long(leaveId));
		if ("未审批".equals(leave.getLeaveStaus())) {
			try {
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("applyUserId", SessionUtil.getUserId(session));
				leaveService.startWorkflow(leaveId, variables);
				return new AJAXResultMessage(true, "操作成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new AJAXResultMessage(false, "操作失败!");
			}
		} else if ("审批中...".equals(leave.getLeaveStaus())) {
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
	 *            通过会话获取登录用户(请假人)
	 * @return
	 */
	@RequestMapping(value = "/findTasks")
	public @ResponseBody Page<LeaveDTO> findTodoTasks(HttpSession session, ExtPageable pageable) {
		Page<LeaveDTO> page = new PageImpl<LeaveDTO>(new ArrayList<LeaveDTO>(), pageable.getPageable(), 0);
		try {
			page = leaveService.findTodoTasks(SessionUtil.getUserId(session), pageable.getPageable());
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
		try {
			leaveService.claim(taskId, SessionUtil.getUserId(session));
			return new AJAXResultMessage(true, "任务签收成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new AJAXResultMessage(false, "任务签收失败!");
		}
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
				String businessKey = leaveService.getBusinessByTaskId(taskId);
				Leave leave = leaveService.findOne(new Long(businessKey));
				leave.setLeaveStaus("申请不通过");
				leaveService.save(leave);
				leaveService.complete(taskId, variables);
			} else {
				Position position = iPositionService.findByPositionName("人事部经理");
				if (position != null) {
					User user = position.getUsers().toArray(new User[] {})[0];
					if (user != null) {
						variables.put("userId", user.getId());
						leaveService.complete(taskId, variables);
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
