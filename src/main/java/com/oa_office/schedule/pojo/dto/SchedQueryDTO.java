package com.oa_office.schedule.pojo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa_office.schedule.pojo.ScheduleHead;

public class SchedQueryDTO {
   private String id;
   private int calendarId = 1;
   private Date startDate;
   private Date endDate;
   private boolean allDay=false;
   private String title = "未定义";
   private String description= "空";
   
   public static List<SchedQueryDTO> schelBodyTODTO(List<ScheduleHead> schelduleBody){
	   List<SchedQueryDTO> schels = new  ArrayList<SchedQueryDTO>();
	   for (ScheduleHead sche : schelduleBody) {
		   SchedQueryDTO shcelBodyDTO = new SchedQueryDTO();
		   shcelBodyDTO.setId(sche.getId());
		   shcelBodyDTO.setStartDate(sche.getBegintime());
		   shcelBodyDTO.setEndDate(sche.getEndtime());
		   shcelBodyDTO.setTitle(sche.getTitle());
		   shcelBodyDTO.setDescription(sche.getContent());
		   schels.add(shcelBodyDTO);
	}
	   
	   return schels;
   }
   
   
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public int getCalendarId() {
	return calendarId;
}
public void setCalendarId(int calendarId) {
	this.calendarId = calendarId;
}
@JsonFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",  timezone = "GMT")
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
@JsonFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",  timezone = "GMT")
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public boolean isAllDay() {
	return allDay;
}
public void setAllDay(boolean allDay) {
	this.allDay = allDay;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
   
   
  
   
}
