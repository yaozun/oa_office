package com.oa_office.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oa_office.schedule.pojo.ScheduleHead;
import com.oa_office.user.pojo.User;
@org.springframework.stereotype.Repository
public interface ScheduleDao extends CrudRepository<ScheduleHead,String>,JpaSpecificationExecutor<ScheduleHead>{
     
	@Query("From ScheduleHead sh where sh.userId = ?1")
    public List<ScheduleHead> findByUserId(String userId);
}
