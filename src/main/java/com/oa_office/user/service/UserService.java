package com.oa_office.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.user.dao.UserRepository;
import com.oa_office.user.pojo.User;

@Service
@Transactional
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;

	// 新增或更新用户
	@Override
	public void saveOrUpdate(User user) {
		userRepository.save(user);
	}

	// 删除用户
	@Override
	public void delete(User user) {
		userRepository.delete(user);

	}

	// 批量删除用户
	@Override
	public void deleteBatch(List<User> users) {

		userRepository.delete(users);
	}

	// 根据Id查找用户
	@Override
	public User findOne(String id) {
		return userRepository.findOne(id);
	}

	// 查询所有用户
	@Override
	public Page<User> findAll(Specification<User> spec, Pageable pageable) {
		return (Page<User>) userRepository.findAll(spec, pageable);
	}

	// 根据用户名和密码查找用户
	@Override
	public User findByUserNameAndPass(User user){
		
		return userRepository.findByUserNameAndPass(user.getUserName(), user.getPassword());
	}
	
	

}
