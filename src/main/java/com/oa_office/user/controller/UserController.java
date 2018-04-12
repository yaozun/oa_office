package com.oa_office.user.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.common.util.ExtPageable;
import com.oa_office.common.util.SessionUtil;
import com.oa_office.menu.service.IMenuService;
import com.oa_office.position.pojo.Position;
import com.oa_office.position.service.IPositionService;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.service.IRoleService;
import com.oa_office.user.pojo.User;
import com.oa_office.user.pojo.dto.UserEditDTO;
import com.oa_office.user.pojo.dto.UserQueryDTO;
import com.oa_office.user.service.IUserService;

@Controller
// @RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IMenuService menuService;

	List<String> authoritys = new ArrayList<String>();

	// 上传用户头像
	@RequestMapping(value = "/user/saveHeadImage", method = RequestMethod.POST)
	public @ResponseBody ExtAjaxResponse saveUser(
			@RequestParam(value = "headFile", required = false) MultipartFile headFile, HttpServletRequest request) {
		User user = userService.findOne(SessionUtil.getUserId(request.getSession()));
		if (user != null) {
			// 文件保存路径
			if (headFile != null) {
				// 上传的文件名
				String filename = headFile.getOriginalFilename();
				String suffixName = filename.substring(filename.lastIndexOf("."));
				if (".jpg".equals(suffixName) || ".png".equals(suffixName)) {
					// 文件的扩张名
					String extensionName = UUID.randomUUID() + suffixName;
					String filePath = request.getSession().getServletContext().getRealPath("\\") + "headImages\\"
							+ extensionName;
					user.setImageUrl(extensionName);
					// 转存文件
					try {
						headFile.transferTo(new File(filePath));
						userService.saveOrUpdate(user);
						SessionUtil.setUser(request.getSession(), user);
						return new ExtAjaxResponse(true, "操作成功");
					} catch (Exception e) {
						e.printStackTrace();
						return new ExtAjaxResponse(false, "操作失败");
					}
				} else {
					return new ExtAjaxResponse(false, "上传的图片的格式错误");
				}

			}
			return new ExtAjaxResponse(false, "操作失败");
		}
		return new ExtAjaxResponse(false, "操作失败");
	}

	// 新增或更新用户
	@RequestMapping("/user/saveAndUpdate")
	public @ResponseBody ExtAjaxResponse saveAndUpdateUser(UserEditDTO userEditDTO) {
		try {
			User user;
			if (userEditDTO.getId() != null) {
				user = userService.findOne(userEditDTO.getId());// 更新
			} else {
				// 默认给新员工添加角色
				user = new User();// 增加
				Role role = roleService.findOne("2c9b8a47609589510160958973880002");
				user.getRoles().add(role);
			}
			Position position = positionService.findByPositionName(userEditDTO.getPosition());
			BeanUtils.copyProperties(userEditDTO, user);
			user.setPosition(position);
			//System.out.println(user);
			/*
			 * user.setUserName(userEditDTO.getUserName());
			 * user.setPassword(userEditDTO.getPassword());
			 * user.setRealName(userEditDTO.getRealName());
			 * user.setGender(userEditDTO.getGender()); user.setAge(userEditDTO.getAge());
			 * user.setPhone(userEditDTO.getPhone()); user.setEmail(userEditDTO.getEmail());
			 * user.setPayment(userEditDTO.getPayment());
			 * user.setBirhthday(userEditDTO.getBirhthday());
			 * user.setHiredate(userEditDTO.getHiredate());
			 * user.setLeavedate(userEditDTO.getLeavedate()); user.setPosition(position);//
			 * 增加时设置为级联更新
			 */ userService.saveOrUpdate(user);
			return new ExtAjaxResponse(true, "操作成功");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false, "操作失败");
		}
	}

	// 删除用户
	@RequestMapping("/user/delete")
	public @ResponseBody ExtAjaxResponse delete(User user) {
		try {
			user.setRoles(null);
			userService.delete(user);
			return new ExtAjaxResponse(true, "操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败");
		}

	}

	// 批量删除用户
	@RequestMapping("/user/deleteBatch")
	public @ResponseBody ExtAjaxResponse deleteBatch(List<User> users) {
		try {
			for (User user : users) {
				user.setRoles(null);
			}
			userService.deleteBatch(users);
			return new ExtAjaxResponse(true, "操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败");
		}
	}

	/*
	 * 未测试
	 * 
	 * 根据用户名userName快速查询; 根据真实姓名realName快速查询; 根据电话号码phone快速查询;
	 * 用户对应的部门（多对一）position快速查询（未实现）;
	 * 
	 */
	// 查询所有用户
	@RequestMapping("/user/findAll")
	public @ResponseBody Page<User> findAll(UserQueryDTO userQueryDTO, ExtPageable extPageable) {
		return userService.findAll(userQueryDTO.getSpecification(userQueryDTO), extPageable.getPageable());
	}

	// 根据Id查找用户
	@RequestMapping("/user/findOne")
	public @ResponseBody User findOne(String id) {
		return userService.findOne(id);
	}

	/*
	 * 用户登陆
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String findOne(User user, HttpSession session, HttpServletRequest request) {
		try {
			if (!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())) {
				User user1 = userService.findByUserNameAndPass(user);
				if (user1 != null) {
					SessionUtil.setUser(session, user1);
					return "redirect:index.jsp";
				}
			}
			request.setAttribute("msg", "用户名或者密码错误");
			return "login";
		} catch (Exception e) {
			request.setAttribute("msg", "用户名或者密码错误");
			return "login";
		}

	}

	/*
	 * 用户登出
	 * 
	 */
	@RequestMapping(value = "/loginOut")
	public String loginOut(HttpSession session) {
		SessionUtil.removeAttribute(session);
		return "redirect:login.jsp";
	}

	/*
	 * protected void setAuthorityList(TreeNode treeNode) { String viewType =
	 * treeNode.getViewType(); if (!StringUtils.isEmpty(viewType)) {
	 * authoritys.add(viewType); } if (treeNode.getChildren() != null &&
	 * treeNode.getChildren().size() > 0) { for (TreeNode treeNode1 :
	 * treeNode.getChildren()) { setAuthorityList(treeNode1); } } }
	 */

	/*
	 * 
	 * 个人中心
	 */
	@RequestMapping("/user/findOneBySession")
	public @ResponseBody User findOneBySession(HttpSession session) {
		User user = userService.findOne(SessionUtil.getUserId(session));
		return user;
	}
}
