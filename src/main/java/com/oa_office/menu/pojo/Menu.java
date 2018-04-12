package com.oa_office.menu.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.oa_office.role.pojo.Role;

@Entity
@Table(name = "menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Menu {
	private String id;
	private String menuname;
	private String menucode;
	private String menuurl;
	private String iconcls;//图标
	private Set<MenuChird> menuChird = new HashSet<MenuChird>();
	private Set<Role> roles = new HashSet<Role>();

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 35)
	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	@OneToMany(/*cascade=CascadeType.ALL,*/mappedBy="menu")
	public Set<MenuChird> getMenuChird() {
		return menuChird;
	}

	public void setMenuChird(Set<MenuChird> menuChird) {
		this.menuChird = menuChird;
	}

	@Column(length = 20, nullable = false)
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	@Column(length = 20, nullable = false)
	public String getmenucode() {
		return menucode;
	}

	public void setmenucode(String menucode) {
		this.menucode = menucode;
	}

	@Column(length = 50, nullable = false)
	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
    
	@ManyToMany(mappedBy = "menus")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Column(length=35,nullable=false)
	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuname=" + menuname + ", menucode=" + menucode + ", menuurl=" + menuurl + "]";
	}

}
