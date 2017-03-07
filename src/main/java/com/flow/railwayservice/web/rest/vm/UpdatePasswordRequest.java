package com.flow.railwayservice.web.rest.vm;

import java.io.Serializable;

public class UpdatePasswordRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	
	public UpdatePasswordRequest(){}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
}
