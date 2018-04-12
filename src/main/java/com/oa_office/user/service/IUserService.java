package com.oa_office.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa_office.user.pojo.User;

public interface IUserService {
	
	//新增或更新用户
	public void saveOrUpdate(User user);
    
	//删除用户
	public void delete(User user);
    
	//批量删除用户
	public void deleteBatch(List<User> users);
    
	//查询所有用户
	public Page<User> findAll(Specification<User> spec,Pageable pageable);
    
	//根据Id查找用户
	public User findOne(String id);
	
	//根据用户名和密码查找用户
	public User findByUserNameAndPass(User user);

}




