package com.oa_office.reimburse.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="re_project")
public class ReProject {

	// 具体项目id
	private String id;
	// 具体报销项目名称
	private String name;
	// 该项目报销费用
	private double pay;
    
	@Id
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
	@Column(length = 20,name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	@Column(name="pay")
	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

}
