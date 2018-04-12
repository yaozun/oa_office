package com.oa_office.dept.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa_office.dept.pojo.Dept;


public interface IDeptService {
	public void saveOrUpdate(Dept dept);
	public void delete(Dept dept);
	//增加批量删除
	public void delete(List<String> ids);
	public Dept findOne(String id);
	public List<Dept> findAll();
	//动态条件查询
	public Page<Dept> findAll(Specification<Dept> spec, Pageable pageable);
	//根据部门名称查找部门
	public Dept findByDeptName(String deptName); 
}
