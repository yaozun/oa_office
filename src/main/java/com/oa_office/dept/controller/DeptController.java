package com.oa_office.dept.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.common.util.ExtJsonResult;
import com.oa_office.common.util.ExtPageable;
import com.oa_office.dept.pojo.Dept;
import com.oa_office.dept.pojo.dto.DeptQueryDTO;
import com.oa_office.dept.service.IDeptService;
import com.oa_office.position.pojo.Position;

@Controller
@RequestMapping("/dept")
public class DeptController {

	@Autowired
	private IDeptService deptService;
	
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Dept dept) {
		try {
			deptService.saveOrUpdate(dept);
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam String id) {
		try {
			Dept dept = deptService.findOne(id);
			if(dept != null) {
				deptService.delete(dept);
			}
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/deleteDepts")//批量删除
	public @ResponseBody ExtAjaxResponse deleteDepts(@RequestParam String[] ids) {
		try {
			List<String> lists = Arrays.asList(ids);
			if(lists != null) {
				deptService.delete(lists);
			}
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/findOne")
	public @ResponseBody Dept findOne(@RequestParam String id) {
		return deptService.findOne(id);
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody List<Dept> findAll() {
		return deptService.findAll();
	}
	
	@RequestMapping("/findPositionAll")
	public @ResponseBody ExtJsonResult<Dept> findUserAll() 
	{
		List<Dept> list = deptService.findAll();
		return new ExtJsonResult<>(list);
	} 
	
	@RequestMapping("/findPage")
	public @ResponseBody Page<Dept> findPage(DeptQueryDTO deptQueryDTO,ExtPageable pageable){
		return deptService.findAll(deptQueryDTO.getSpecification(deptQueryDTO), pageable.getPageable());
	}
	
}
