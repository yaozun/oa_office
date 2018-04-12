package com.oa_office.position.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.position.dao.PositionRepository;
import com.oa_office.position.pojo.Position;

@Service
@Transactional
public class PositionService implements IPositionService {
	
	@Autowired
	private PositionRepository positionRepository;

	@Override
	public void saveOrUpdate(Position position) {
		positionRepository.save(position);
	}

	@Override
	public void delete(Position position) {
		positionRepository.delete(position);
	}

	//批量删除
	@Override
	public void delete(List<String> ids) {
		List<Position> positions = (List<Position>) positionRepository.findAll(ids);
		if(positions != null) {
			positionRepository.delete(positions);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Position findOne(String id) {
		return positionRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Position> findAll() {
		return (List<Position>) positionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Position> findAll(Specification<Position> spec, Pageable pageable) {
		
		return positionRepository.findAll(spec, pageable);
	}

	@Override
	public Position findByPositionName(String positionName) {
		return positionRepository.findByPositionName(positionName);
	}

}
