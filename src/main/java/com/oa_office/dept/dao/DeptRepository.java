package com.oa_office.dept.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa_office.dept.pojo.Dept;
@Repository
public interface DeptRepository extends PagingAndSortingRepository<Dept, String>, JpaSpecificationExecutor<Dept> {

	@Query("from Dept dept where dept.deptName = ?1")
	public Dept findByDeptName(String deptName);
	
}
