package com.flow.railwayservice.web.rest.vm;

import java.io.Serializable;

/**
 * Simple value request
 * @author Dayna
 *
 */
public class SimpleRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	
	public SimpleRequest(){}
	
	public SimpleRequest(String value){
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public void setValue(Object value){
		this.value = value;
	}
}
