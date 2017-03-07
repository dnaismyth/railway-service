package com.flow.railwayservice.web.rest.vm;

import java.io.Serializable;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.flow.railwayservice.dto.OperationType;

/**
 * Response for the case when password is updated,
 * Generate a new access token.
 * @author Dayna
 *
 */
public class UpdatePasswordResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3345718634452569475L;
	
	private OperationType operationType;
	private OAuth2AccessToken token;
	
	public UpdatePasswordResponse(){}
	
	public UpdatePasswordResponse(OperationType operationType, OAuth2AccessToken token){
		this.operationType = operationType;
		this.token = token;
	}
	
	public UpdatePasswordResponse(OperationType operationType){
		this.operationType = operationType;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public OAuth2AccessToken getToken() {
		return token;
	}

	public void setToken(OAuth2AccessToken token) {
		this.token = token;
	}

}
