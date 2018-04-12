package com.oa_office.news.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "news")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class News {
	private String id;
	private String title;// 消息概要
	private String text;// 消息内容
	private int status;// 消息状态 有效-1 失效-0
	private Date endtime;// 失效日期

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false, columnDefinition = "TEXT")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(columnDefinition = "TEXT", nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(length = 2)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	@Column(nullable = false)
	public Date getendtime() {
		return endtime;
	}

	public void setendtime(Date endtime) {
		this.endtime = endtime;
	}

}
