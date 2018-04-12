package com.oa_office.user.dao;


import com.oa_office.user.pojo.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@org.springframework.stereotype.Repository
public interface UserRepository extends CrudRepository<User,Long>,JpaSpecificationExecutor<User>
{   
	
	/*
	 * 
	 * 通过Id查询用户
	 */
    @Query("from User user where user.id = ?1")
	public User findOne(String id);
    
    @Query(value="select * from User user where user.user_name=?1 and user.password=?2",nativeQuery=true)
    public User findByUserNameAndPass(String userName,String password);
    

}
