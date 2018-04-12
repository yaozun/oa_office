package com.oa_office.position.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.oa_office.position.pojo.Position;

/**
 * 职位Query DTO 封装查询条件 数据传输对象
 */
public class PositionQueryDTO {

	private String positionName;

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	// 提供static的工具方法：根据当前 QueryDTO 对象来组装动态查询条件
	public static Specification<Position> getSpecification(PositionQueryDTO positionQueryDTO) {

		Specification<Position> spec = new Specification<Position>() {

			@Override
			public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.Predicate查询条件集合
				List<Predicate> list = new ArrayList<Predicate>();
				// 2.根据 QueryDTO数据字段的值进行判断以及条件的组装
				if (null != positionQueryDTO && !StringUtils.isEmpty(positionQueryDTO.getPositionName())) {
					Predicate p1 = cb.like(root.get("positionName").as(String.class),
							"%" + positionQueryDTO.getPositionName() + "%");
					list.add(p1);
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
