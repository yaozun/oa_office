package com.oa_office.dept.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.dept.dao.DeptRepository;
import com.oa_office.dept.pojo.Dept;
@Service
@Transactional
public class DeptService implements IDeptService {

	@Autowired
	private DeptRepository deptRepository;
	
	@Override
	public void saveOrUpdate(Dept dept) {
		deptRepository.save(dept);
	}

	@Override
	public void delete(Dept dept) {
		deptRepository.delete(dept);
	}

	@Override
	public void delete(List<String> ids) {		
		List<Dept> depts = (List<Dept>) deptRepository.findAll(ids);
		if(depts != null) {
			deptRepository.delete(depts);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Dept findOne(String id) {
		return deptRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dept> findAll() {
		return (List<Dept>) deptRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dept> findAll(Specification<Dept> spec, Pageable pageable) {
		return deptRepository.findAll(spec, pageable);
	}

	@Override
	public Dept findByDeptName(String deptName) {
		return deptRepository.findByDeptName(deptName);
	}

}
