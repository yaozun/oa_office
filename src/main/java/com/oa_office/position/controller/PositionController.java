package com.oa_office.position.controller;

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
import com.oa_office.dept.service.IDeptService;
import com.oa_office.position.pojo.Position;
import com.oa_office.position.pojo.dto.PositionDTO;
import com.oa_office.position.pojo.dto.PositionQueryDTO;
import com.oa_office.position.service.IPositionService;

@Controller
@RequestMapping("/position")
public class PositionController {
	
	@Autowired
	private IPositionService positionService;
	@Autowired
	private IDeptService deptService;
	
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(PositionDTO positionDTO) {
		try {
			Position position;
			if(positionDTO.getId() != null) {
				position = positionService.findOne(positionDTO.getId());//更新
			}else {
				position = new Position();//新增
				position.setUsers(null);
			}
			Dept dept = deptService.findByDeptName(positionDTO.getDept());
			position.setPositionName(positionDTO.getPositionName());
			position.setDept(dept);
			positionService.saveOrUpdate(position);
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam String id) {
		try {
			Position position = positionService.findOne(id);
			if(position != null) {
				positionService.delete(position);
			}
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/deletePositions")//批量删除
	public @ResponseBody ExtAjaxResponse deletePositions(@RequestParam String[] ids) {
		try {
			List<String> lists = Arrays.asList(ids);
			if(lists != null) {
				positionService.delete(lists);
			}
			return new ExtAjaxResponse(true,"操作成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"操作失败");
		}
	}
	
	@RequestMapping("/findOne")
	public @ResponseBody Position findOne(@RequestParam String id) 
	{
		return positionService.findOne(id);
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody List<Position> findAll() 
	{
		return positionService.findAll();
	}
	
	@RequestMapping("/findUserAll")
	public @ResponseBody ExtJsonResult<Position> findUserAll() 
	{
		List<Position> list = positionService.findAll();
		return new ExtJsonResult<>(list);
	}
	
	@RequestMapping("/findPage")
	public @ResponseBody Page<Position> findPage(PositionQueryDTO positionQueryDTO,ExtPageable pageable){
		return positionService.findAll(positionQueryDTO.getSpecification(positionQueryDTO), pageable.getPageable());
	}

}
