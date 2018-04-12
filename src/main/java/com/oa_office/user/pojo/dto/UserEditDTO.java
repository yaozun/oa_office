package com.oa_office.user.pojo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserEditDTO {

	// 用户ID
	private String id;
	// 用户名
	private String userName;
	// 密码
	private String password;
	// 真实姓名
	private String realName;
	// 性别
	private String gender;
	// 年龄
	private Integer age;
	// 电话号码
	private String phone;
	// 邮箱
	private String email;
	// 工资
	private double payment;
	// 出生日期
	
	private Date birhthday;
	// 入职日期
	
	private Date hiredate;
	// 离职日期
	
	private Date leavedate;
	// 头像存放的地址
	private String imageUrl;
	// position的id
	private String position;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	public Date getBirhthday() {
		return birhthday;
	}
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setBirhthday(Date birhthday) {
		this.birhthday = birhthday;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	public Date getHiredate() {
		return hiredate;
	}
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	public Date getLeavedate() {
		return leavedate;
	}
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
