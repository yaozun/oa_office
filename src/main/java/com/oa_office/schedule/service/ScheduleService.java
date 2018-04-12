package com.oa_office.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.schedule.dao.ScheduleDao;
import com.oa_office.schedule.pojo.ScheduleHead;
@Service
@Transactional
public class ScheduleService implements IScheduleService{
    @Autowired
    private ScheduleDao scheduleDao;
	@Override
	public void saveOrUpdate(ScheduleHead scheduleHead) {
		
		scheduleDao.save(scheduleHead);
	}

	@Override
	public void delete(ScheduleHead scheduleHead) {	
		scheduleDao.delete(scheduleHead);
	}

	@Override
	public void delete(List<ScheduleHead> scheduleHeads) {
		scheduleDao.delete(scheduleHeads);
		
	}

	@Override
	public List<ScheduleHead> findAll(List<String> ids) {
		
		return (List<ScheduleHead>) scheduleDao.findAll(ids);
	}

	@Override
	public ScheduleHead findOne(String id) {
		
		return scheduleDao.findOne(id);
	}

	@Override
	public List<ScheduleHead> findAll() {
		
		return (List<ScheduleHead>) scheduleDao.findAll();
	}
	
	@Override
	public List<ScheduleHead> findByUserId(String userId){
		return scheduleDao.findByUserId(userId);
	}

}
