package com.oa_office.position.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa_office.position.pojo.Position;

public interface IPositionService {

	public void saveOrUpdate(Position position);
	public void delete(Position position);
	//增加批量删除
	public void delete(List<String> ids);
	public Position findOne(String id);
	public List<Position> findAll();
	//动态条件查询
	public Page<Position> findAll(Specification<Position> spec, Pageable pageable);
	//根据职位名称获取类
	public Position findByPositionName(String positionName);
	
}
