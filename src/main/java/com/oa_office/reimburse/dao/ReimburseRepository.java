package com.oa_office.reimburse.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa_office.leave.entity.Leave;
import com.oa_office.reimburse.pojo.Reimburse;

@Repository
public interface ReimburseRepository extends PagingAndSortingRepository<Reimburse, String>,JpaSpecificationExecutor<Reimburse>
{
	@Query("from Reimburse reimburse where reimburse.userId = ?1") 
	public Page<Reimburse> findReimburse(String userId,Pageable pageable); 
}
