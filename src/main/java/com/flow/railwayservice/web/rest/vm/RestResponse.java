package com.flow.railwayservice.web.rest.vm;

import com.flow.railwayservice.dto.OperationType;

public class RestResponse<T> {

	private T data;
	private OperationType op;
	
	public RestResponse(){}
	
	public RestResponse(T data){
		this.data = data;
	}
	
	public RestResponse(T data, OperationType op){
		this.data = data;
		this.op = op;
	}
	
	public T getData(){
		return data;
	}
	
	public void setData(T data){
		this.data = data;
	}
	
	public OperationType getOperationType(){
		return op;
	}
	
	public void setOperationType(OperationType op){
		this.op = op;
	}
}
