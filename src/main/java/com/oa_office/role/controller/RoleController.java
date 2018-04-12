package com.oa_office.role.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.news.pojo.News;
import com.oa_office.role.pojo.Role;
import com.oa_office.role.pojo.dto.RoleQueryDTO;
import com.oa_office.role.service.IRoleService;

@Controller
@RequestMapping("/role/")
public class RoleController {
	@Autowired
	private IRoleService iRoleService;
	// 添加角色
	@RequestMapping(value = "save")
    public @ResponseBody ExtAjaxResponse saveRole(String rolecode,String rolename,String id){   
		try {
		Role role = new Role();
		role.setid(id);
		role.setRolecode(rolecode);
		role.setRolename(rolename);
		iRoleService.saveOrUpdate(role);
		return new ExtAjaxResponse(true,"操作成功！");
	} catch (Exception e) {
		System.out.println(e);
		return new ExtAjaxResponse(false,"操作失败！");
	}
  }
// 显示所有角色
	@RequestMapping(value = "show_all")
    public @ResponseBody List<RoleQueryDTO> showAll(){     
		List<Role> roles = iRoleService.findAll();
		List<RoleQueryDTO> dtos = RoleQueryDTO.roleTOdto(roles);
   return dtos;
  }
	// 删除角色
	@RequestMapping(value = "delete")
    public @ResponseBody ExtAjaxResponse delete(String roleid){     
		try {
			Role role = iRoleService.findOne(roleid);
			iRoleService.delete(role);
			return new ExtAjaxResponse(true,"操作成功！");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false,"操作失败！");
		}
  }
}
