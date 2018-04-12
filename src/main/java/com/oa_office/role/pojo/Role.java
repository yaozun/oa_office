package com.oa_office.role.pojo;

import java.util.HashSet;
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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.user.pojo.User;

@Entity
@Table(name = "role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Role {
	private String id;
	private String rolename;
	private String rolecode;
	private Set<Menu> menus = new HashSet<Menu>();
	private Set<User> users = new HashSet<User>();

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 35)
	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	@Column(length = 20,nullable = false)
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@Column(length = 20,nullable = false,unique = true)
	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER)
	@JoinTable(name = "role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
    //user表引用自己的主键,由user来维护关系
	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}
   
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	

}
