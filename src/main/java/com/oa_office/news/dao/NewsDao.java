package com.oa_office.news.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.oa_office.news.pojo.News;
@org.springframework.stereotype.Repository
public interface NewsDao extends CrudRepository<News,String>,JpaSpecificationExecutor<News>{
	List<News> findAllByStatus(int status);
}
