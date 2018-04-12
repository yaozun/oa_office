package com.oa_office.news.service;

import java.util.List;

import com.oa_office.news.pojo.News;

public interface INewsService {
	public void saveOrUpdate(News news);
	public void delete(News news);
	public void delete(List<News> newss);
	public List<News> findAll(List<String> ids);
	public News findOne(String id);
	public List<News> findAll();
	public List<News> findEfect(int status);

}
