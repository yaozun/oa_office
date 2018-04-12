package com.oa_office.user.pojo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import com.oa_office.position.pojo.Position;
import com.oa_office.user.pojo.User;

public class UserQueryDTO {

	// 用户名
	private String userName;
	// 真实姓名
	private String realName;
	// 电话号码
	private String phone;
	// 用户对应的部门（多对一）
	private String position;
	
	private double payment;
	
	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	// 提供static的工具方法：根据当前 userQueryDTO 对象来组装动态查询条件
	public static Specification<User> getSpecification(UserQueryDTO userQueryDTO) {
		Specification<User> spec = new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.Predicate查询条件集合
				List<Predicate> list = new ArrayList<Predicate>();

				// 2.根据 QueryDTO数据字段的值进行判断以及条件的组装
				if (null != userQueryDTO && !StringUtils.isEmpty(userQueryDTO.getUserName())) {
					Predicate p1 = cb.like(root.get("userName").as(String.class),
							"%" + userQueryDTO.getUserName() + "%");
					list.add(p1);
				}
				
				if (null != userQueryDTO && !StringUtils.isEmpty(userQueryDTO.getRealName())) {
					Predicate p2 = cb.like(root.get("realName").as(String.class),
							"%" + userQueryDTO.getRealName() + "%");
					list.add(p2);
				}
				
				if (null != userQueryDTO && !StringUtils.isEmpty(userQueryDTO.getPhone())) {
					Predicate p3 = cb.like(root.get("phone").as(String.class),
							"%" + userQueryDTO.getPhone() + "%");
					list.add(p3);
				}
				
				if (null != userQueryDTO && !StringUtils.isEmpty(userQueryDTO.getPhone())) {
					Predicate p4 = cb.like(root.get("position").as(String.class),
							"%" + userQueryDTO.getPhone() + "%");
					list.add(p4);
				}
				
				// 3.Predicate查询条件集合的 size 创建对应的Predicate查询条件数组
				Predicate[] p = new Predicate[list.size()];
				// 4.CriteriaBuilder的and 函数组装 查询条件数组
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}

}
