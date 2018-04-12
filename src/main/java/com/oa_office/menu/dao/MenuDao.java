package com.oa_office.menu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oa_office.menu.pojo.Menu;
import com.oa_office.menu.pojo.MenuChird;
import com.oa_office.position.pojo.Position;
@org.springframework.stereotype.Repository
public interface MenuDao extends CrudRepository<Menu,String>,JpaSpecificationExecutor<Menu>{
	@Query(value="delete from role_menu where  menu_id = ?1",nativeQuery=true)
	void deleteRMId(String menuid);
	
	@Query("from Menu menu where menu.menuname = ?1")
	public Menu findByMenuName(String menuName);
}
