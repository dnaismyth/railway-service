package com.flow.railwayservice.web.rest.vm;

import org.springframework.data.domain.Page;

/**
 * VM For Page Response
 * @author Dayna
 *
 * @param <T>
 */
public class PageResponse<T> {

	private Page<T> dataPage;
	private int count;
	
	public PageResponse(Page<T> dataPage){
		this.dataPage = dataPage;
		this.count = dataPage.getNumberOfElements();
	}
		
	public PageResponse(){}
	
	
	public void setPage(Page<T> dataPage){
		this.dataPage = dataPage;
	}
	
	public Page<T> getPage(){
		return dataPage;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
}