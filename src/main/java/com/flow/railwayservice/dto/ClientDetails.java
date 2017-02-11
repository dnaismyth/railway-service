package com.flow.railwayservice.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class ClientDetails {
	
	private Set<String> scope = new HashSet<String>();
	private Set<GrantedAuthority> authorities;
	private Set<String> resourceIds = new HashSet<String>();
	private Platform platform;
	private String clientId;
	
	public ClientDetails(String clientId, Set<GrantedAuthority> authorities){
		mapClientDetails(clientId);
		this.clientId = clientId;
		this.authorities = authorities;
	}
	
	private void mapClientDetails(String clientId){
		switch(clientId){
			case "railwayservice-ios":
				scope.add("read");
				scope.add("write");
				resourceIds.add("res_railwayservice");
				platform = Platform.APNS;
				break;
			case "railwayservice-web":
				scope.add("read");
				scope.add("write");
				resourceIds.add("res_railwayservice");
				platform = Platform.WEB;
				break;
			default:
				break;
			
		}
	}

	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Set<String> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}
