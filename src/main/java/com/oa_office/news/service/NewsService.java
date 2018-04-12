package com.oa_office.news.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa_office.news.pojo.News;
import com.oa_office.news.dao.NewsDao;
@Service
@Transactional
public class NewsService implements INewsService{
	@Autowired
	private NewsDao newsDao;

	@Override
	public void saveOrUpdate(News news) {
		
		newsDao.save(news);
	}

	@Override
	public void delete(News news) {
		
		newsDao.delete(news);
	}

	@Override
	public void delete(List<News> newss) {
		newsDao.delete(newss);
		
	}

	@Override
	public List<News> findAll(List<String> ids) {
		
		return (List<News>) newsDao.findAll(ids);
	}

	@Override
	public News findOne(String id) {
		
		return newsDao.findOne(id);
	}

	@Override
	public List<News> findAll() {
		
		return (List<News>) newsDao.findAll();
	}

	@Override
	public List<News> findEfect(int status) {
		return newsDao.findAllByStatus(status);
	}

}
