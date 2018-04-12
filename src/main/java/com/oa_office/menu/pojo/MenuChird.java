package com.oa_office.menu.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.oa_office.role.pojo.Role;

@Entity
@Table(name = "menu_chird")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class MenuChird {
	private String id;
	private String menuname;
	private String menucode;
	private String menuurl;
	private String iconcls;//图标
	private Menu menu; //父菜单id

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 35)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 20, nullable = false)
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	@Column(length = 20, nullable = false, unique = true)
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

	
	@Column(length=35,nullable=false)
	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="menu_id")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
    
	
	
	
}
