package com.oa_office.schedule.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa_office.user.pojo.User;
@Entity
@Table(name = "schedule_head")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ScheduleHead {

	private String id;
	private String title;
	private String content;
	private Date begintime;
	private Date endtime;
	private String userId;
	
	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	@Column(length = 35,nullable = false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(columnDefinition="TEXT",nullable = false) 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	@Column(nullable = false) 
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	@Column(nullable = false) 
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@Column(length = 35,name="user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
}
