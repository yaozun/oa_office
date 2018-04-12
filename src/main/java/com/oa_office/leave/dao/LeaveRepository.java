package com.oa_office.leave.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa_office.leave.entity.Leave;

@Repository
public interface LeaveRepository extends PagingAndSortingRepository<Leave, Long>,JpaSpecificationExecutor<Leave>
{
	@Query("from Leave leave where leave.userId = ?1") 
	public Page<Leave> findLeave(String userId,Pageable pageable); 
}
