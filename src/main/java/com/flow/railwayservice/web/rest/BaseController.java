package com.flow.railwayservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.service.UserService;

public class BaseController {
	
	protected static final String PAGE_PARAM = "page";
	protected static final String SIZE_PARAM =  "size";
	protected static final String LAT_PARAM = "lat";
	protected static final String LON_PARAM = "lon";
	protected static final String RADIUS_PARAM = "radius";
	protected static final int DEFAULT_RADIUS = 100;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Return the current logged in user
	 * @return
	 */
	protected User getCurrentUser(){
		User current = userService.getCurrentUserDTOWithAuthorities();
		return current;
	}
}
