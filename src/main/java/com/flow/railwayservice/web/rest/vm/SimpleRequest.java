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
	private String value;
	
	public SimpleRequest(){}
	
	public SimpleRequest(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
}
