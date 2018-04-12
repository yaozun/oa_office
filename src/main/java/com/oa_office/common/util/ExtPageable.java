package com.oa_office.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class ExtPageable {
	private int  page  = 1;	//pageNumber
	private int  limit = 25;//pageSize
	private String sort ="id";
	private String dir ="ASC";
	
	//只用于要接收前端分页条件（所以只需要set即可）
		public void setPage(int page) {
			this.page = page;
		}
		public void setLimit(int limit) {
			this.limit = limit;
		}
		public void setSort(String sort) {
			this.sort = sort;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		
		public Pageable getPageable() {
			Sort sort = new Sort(Direction.ASC, this.sort);
			if(this.dir.equals("DESC")) {
				sort = new Sort(Direction.DESC, this.sort);
			}
			return new PageRequest(this.page-1, this.limit, sort);
		}


}
