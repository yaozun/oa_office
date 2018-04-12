package com.oa_office.news.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oa_office.common.util.ExtAjaxResponse;
import com.oa_office.news.pojo.News;
import com.oa_office.news.service.INewsService;
@Controller
@RequestMapping("/news/")
public class NewsController {
	@Autowired
	private INewsService iNewsService;
// 添加消息通告，
	@RequestMapping(value = "save",method = RequestMethod.POST)
    public @ResponseBody ExtAjaxResponse addnews(News news){   
		try {
		iNewsService.saveOrUpdate(news);	
		return new ExtAjaxResponse(true,"操作成功！");
	} catch (Exception e) {
		System.out.println(e);
		return new ExtAjaxResponse(false,"操作失败！");
	}
  }
	// 显示生效的消息通告
	@RequestMapping(value = "show_effect")
    public @ResponseBody List<News> showeffect(){     
		
   return iNewsService.findEfect(1);
  }
	// 显示所有通告（按创建时间）
	@RequestMapping(value = "show_all")
    public @ResponseBody List<News> showAll(){     
   return iNewsService.findAll();
  }
	// 删除公告
	@RequestMapping(value = "delete")
    public @ResponseBody ExtAjaxResponse delete(String newsid){     
		try {
			iNewsService.delete(iNewsService.findOne(newsid));
			return new ExtAjaxResponse(true,"操作成功！");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false,"操作失败！");
		}
  }
	// 使通告失效
	@RequestMapping(value = "failure")
    public @ResponseBody ExtAjaxResponse newsfailure(String newsid){     
		try {
			News news = iNewsService.findOne(newsid);
			news.setStatus(0);
			iNewsService.saveOrUpdate(news);
			return new ExtAjaxResponse(true,"操作成功！");
		} catch (Exception e) {
			System.out.println(e);
			return new ExtAjaxResponse(false,"操作失败！");
		}
  }
}
