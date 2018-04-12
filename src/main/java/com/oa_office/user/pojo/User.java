package com.oa_office.user.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.FetchStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oa_office.position.pojo.Position;
import com.oa_office.role.pojo.Role;
import com.oa_office.schedule.pojo.ScheduleHead;

@Entity
@Table(name = "user")
public class User implements Serializable {

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
	//日程
	private Set<ScheduleHead> scheduleHeads = new HashSet<ScheduleHead>();
	// 头像存放的地址
	private String imageUrl;
	// 用户对应的角色
	private Set<Role> roles = new HashSet<Role>();
	// 用户对应的部门（多对一）
	private Position position;

	@Id
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}
	@Column(length = 25, name = "user_name", nullable = false)
	public String getUserName() {
		return userName;
	}

	@Column(length = 36, name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	@Column(length = 25, name = "real_name", nullable = false)
	public String getRealName() {
		return realName;
	}

	@Column(length = 2, name = "gender")
	public String getGender() {
		return gender;
	}

	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	@Column(length = 15, name = "phone")
	public String getPhone() {
		return phone;
	}

	@Column(length = 15, name = "email")
	public String getEmail() {
		return email;
	}

	@Column(name = "payment")
	public double getPayment() {
		return payment;
	}

	@Column(length = 255, name = "image_url")
	public String getImageUrl() {
		return imageUrl;
	}

	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public Date getBirhthday() {
		return birhthday;
	}

	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public Date getHiredate() {
		return hiredate;
	}

	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public Date getLeavedate() {
		return leavedate;
	}

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	@ManyToOne(cascade= CascadeType.MERGE) //级联更新 多对一级联删除，使用ALL建议先移除关系（update）再delete
	public Position getPosition() {
		return position;
	}
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	//@Fetch(org.hibernate.annotations.FetchMode.SELECT)
	public Set<ScheduleHead> getScheduleHeads() {
		return scheduleHeads;
	}

	// ===========================Setter 方法================================//
	public void setId(String id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setBirhthday(Date birhthday) {
		this.birhthday = birhthday;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setScheduleHeads(Set<ScheduleHead> scheduleHeads) {
		this.scheduleHeads = scheduleHeads;
	}

	// ===========================构造方法================================//

	public User(String id, String userName, String password, String realName, String gender, Integer age, String phone,
			String email, double payment, Date birhthday, Date hiredate, Date leavedate) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.payment = payment;
		this.birhthday = birhthday;
		this.hiredate = hiredate;
		this.leavedate = leavedate;
	}

	public User() {
	}
	
	

}
