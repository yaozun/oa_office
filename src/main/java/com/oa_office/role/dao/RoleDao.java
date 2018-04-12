package com.oa_office.role.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oa_office.role.pojo.Role;
@org.springframework.stereotype.Repository
public interface RoleDao extends CrudRepository<Role,String>,JpaSpecificationExecutor<Role>{
 
	@Query(value="select role_id  from role_menu where menu_id = ?1",nativeQuery=true)
    List<String> findRoleid(String menuid);
	
	@Query(value="select menu_id from role_menu where role_id = ?1",nativeQuery=true)
    List<String> findMenuid(String roleid);
}
