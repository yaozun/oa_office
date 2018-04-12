package com.oa_office.menu.pojo.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;

public class MenuQueryDTO {
	private String id;
	  private String menuname;
	  private String menucode;
	  private String menuurl;
	  private String iconcls;//图标
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getmenucode() {
		return menucode;
	}
	public void setmenucode(String menucode) {
		this.menucode = menucode;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	
	public String getIconcls() {
		return iconcls;
	}
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	public static MenuQueryDTO  ToDTO(Menu menu) {
		MenuQueryDTO dtoone = new MenuQueryDTO();
		dtoone.setId(menu.getid());
		dtoone.setMenuname(menu.getMenuname());
		dtoone.setMenuurl(menu.getMenuurl());
		dtoone.setmenucode(menu.getmenucode());
		dtoone.setIconcls(menu.getIconcls());
		return dtoone;
	}
	public static MenuQueryDTO  ToDTO(MenuChird menu) {
		MenuQueryDTO dtoone = new MenuQueryDTO();
		dtoone.setId(menu.getId());
		dtoone.setMenuname(menu.getMenuname());
		dtoone.setMenuurl(menu.getMenuurl());
		dtoone.setmenucode(menu.getmenucode());
		dtoone.setIconcls(menu.getIconcls());
		return dtoone;
	}
	public static List<MenuQueryDTO> ToDTO(List<Menu> menus) {
		List<MenuQueryDTO> dto = new ArrayList<MenuQueryDTO>();
		for(Menu item: menus) {
    		MenuQueryDTO dtoone = new MenuQueryDTO();
    		dtoone.setId(item.getid());
    		dtoone.setMenuname(item.getMenuname());
    		dtoone.setMenuurl(item.getMenuurl());
    		dtoone.setmenucode(item.getmenucode());
    		dtoone.setIconcls(item.getIconcls());
    		dto.add(dtoone);
    	}
		return dto;
	}
	public static List<MenuQueryDTO> sToDTO(List<MenuChird> menus) {
		List<MenuQueryDTO> dto = new ArrayList<MenuQueryDTO>();
		for(MenuChird item: menus) {
    		MenuQueryDTO dtoone = new MenuQueryDTO();
    		dtoone.setId(item.getId());
    		dtoone.setMenuname(item.getMenuname());
    		dtoone.setMenuurl(item.getMenuurl());
    		dtoone.setmenucode(item.getmenucode());
    		dtoone.setIconcls(item.getIconcls());
    		dto.add(dtoone);
    	}
		return dto;
	}
	
	public static Specification<Menu> getSpecification(final MenuQueryDTO menuQueryDTO)
	{
		Specification<Menu> spec = new Specification<Menu>() {
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<Predicate>(); 
				 
				 if(null != menuQueryDTO && !StringUtils.isEmpty(menuQueryDTO.getMenuname())) {
					 Predicate  p1 =  cb.like(root.get("menuname").as(String.class),"%"+ menuQueryDTO.getMenuname() + "%");
					 list.add(p1);
				 }
				 if(null != menuQueryDTO && !StringUtils.isEmpty(menuQueryDTO.getMenuurl())) {
					 Predicate  p2 =  cb.like(root.get("menuurl").as(String.class),"%"+ menuQueryDTO.getMenuurl() + "%");
					 list.add(p2);
				 }
				 Predicate[] p = new Predicate[list.size()];  
				 return cb.and(list.toArray(p));  
			}
		};
		return spec;
	}
}
