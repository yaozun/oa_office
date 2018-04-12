package com.oa_office.menu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oa_office.menu.pojo.MenuChird;
@org.springframework.stereotype.Repository
public interface MenuChirdDao extends CrudRepository<MenuChird,String>,JpaSpecificationExecutor<MenuChird>{
	 @Query(value="select *  from menu_chird where menu_id = ?1",nativeQuery=true)
	List<MenuChird> findAllByMenuId(String menuid);
}
