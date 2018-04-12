package com.oa_office.position.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oa_office.dept.pojo.Dept;
import com.oa_office.user.pojo.User;

@Entity
@Table(name="position")
public class Position {

	//职位主键
	private String id;
	//职位名称
	private String positionName;
	//一对多，一个职位对应多个用户
	private Set<User> users = new HashSet<User>();
	//多对一，一个部门对应多个职位
	private Dept dept;
	
	@Id
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	@Column(length=35,nullable=false,unique=true)
	public String getId() {
		return id;
	}
	@Column(length=25,nullable=false,name="position_name")
	public String getPositionName() {
		return positionName;
	}
	@JsonIgnore
	@OneToMany(mappedBy="position",fetch =FetchType.EAGER)//在一的一方放弃外键维护
	public Set<User> getUsers() {
		return users;
	}
	@ManyToOne(cascade= CascadeType.MERGE)//多对一级联删除，使用ALL建议先移除关系（update）再delete
	public Dept getDept() {
		return dept;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	@Override
	public String toString() {
		return "Position [id=" + id + ", positionName=" + positionName + ", users=" + users + "]";
	}
	
}
