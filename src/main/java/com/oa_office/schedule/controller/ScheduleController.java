package com.oa_office.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.common.util.ExtjsSchedul;
import com.oa_office.common.util.SessionUtil;
import com.oa_office.schedule.pojo.ScheduleHead;
import com.oa_office.schedule.pojo.dto.SchedQueryDTO;
import com.oa_office.schedule.service.IScheduleService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.service.IUserService;

import jodd.util.StringUtil;

@Controller
@RequestMapping("/schedule/")
public class ScheduleController {
	@Autowired
	private IScheduleService iScheduleService;
	@Autowired
	private IUserService userService;

	// 添加和修改日程(id是日程id)
	@RequestMapping(value = "save_head", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse saveHead(String title, String content, String begintime, String endTime,
			String id, HttpSession session) {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begintime1 = null;
			Date endtime1 = null;
			if (!StringUtil.isEmpty(begintime)) {
				begintime1 = formatter.parse(begintime.replace("T", " "));
			}
			if (!StringUtil.isEmpty(endTime)) {
				endtime1 = formatter.parse(endTime.replace("T", " "));
			}
			User user = SessionUtil.getUser(session);
			ScheduleHead schedule = new ScheduleHead();
			if (id == null || id == "") {
				schedule.setTitle(title);
				schedule.setContent(content);
				schedule.setBegintime(begintime1);
				schedule.setEndtime(endtime1);
				schedule.setUserId(user.getId());
				iScheduleService.saveOrUpdate(schedule);
				return new ExtAjaxResponse(true, "操作成功！");
			}
			schedule = iScheduleService.findOne(id);
			schedule.setTitle(title);
			schedule.setContent(content);
			schedule.setBegintime(begintime1);
			schedule.setEndtime(endtime1);
			iScheduleService.saveOrUpdate(schedule);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	// 删除日程内容
	@RequestMapping(value = "delete_body", method = RequestMethod.GET)
	public @ResponseBody ExtAjaxResponse deleteBody(String id) {
		try {
			ScheduleHead schedule = iScheduleService.findOne(id);
			iScheduleService.delete(schedule);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	// 查看日程内容
	@RequestMapping(value = "show_body")
	public @ResponseBody ExtjsSchedul<SchedQueryDTO> showBody(HttpSession session) {
		List<ScheduleHead> scell = iScheduleService.findByUserId(SessionUtil.getUserId(session));
		List<SchedQueryDTO> schs = SchedQueryDTO.schelBodyTODTO(scell);
		ExtjsSchedul<SchedQueryDTO> content = new ExtjsSchedul<SchedQueryDTO>();
		content.setLists(schs);
		return content;
	}

}
