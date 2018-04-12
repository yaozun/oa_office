package com.oa_office.position.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa_office.position.pojo.Position;
@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, String>,JpaSpecificationExecutor<Position> {
	
	@Query("from Position position where position.positionName = ?1")
	public Position findByPositionName(String positionName);
	
}
