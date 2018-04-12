package com.oa_office.dept.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oa_office.position.pojo.Position;

@Entity
@Table(name="dept")
public class Dept {

	//部门主键
	private String id;
	//部门名称
	private String deptName;
	//部门对应的职位(一对多)
	private Set<Position> positions = new HashSet<Position>();
	
	@Id
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	@Column(length=35,nullable=false,unique=true)
	public String getId() {
		return id;
	}
	@Column(length=25,nullable=false,name="dept_name")
	public String getDeptName() {
		return deptName;
	}
	@JsonIgnore//DETACH如果你要删除一个实体，但是它有外键无法删除，你就需要这个级联权限了。它会撤销所有相关的外键关联。
	@OneToMany(mappedBy="dept",fetch =FetchType.EAGER)//在一的一方放弃外键维护
	public Set<Position> getPositions() {
		return positions;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}
}
