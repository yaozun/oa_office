package com.oa_office.schedule.service;

import java.util.List;

import com.oa_office.schedule.pojo.ScheduleHead;

public interface IScheduleService {
	public void saveOrUpdate(ScheduleHead scheduleHead);
	public void delete(ScheduleHead scheduleHead);
	public void delete(List<ScheduleHead> scheduleHeads);
	public List<ScheduleHead> findAll(List<String> ids);
	public ScheduleHead findOne(String id);
	public List<ScheduleHead> findAll();
	//通过userId查找日程
	public List<ScheduleHead> findByUserId(String userId);
}
