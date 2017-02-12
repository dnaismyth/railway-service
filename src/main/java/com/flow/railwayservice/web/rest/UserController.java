package com.flow.railwayservice.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.OperationType;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.UserService;
import com.flow.railwayservice.web.rest.vm.RestResponse;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	
	/**
	 * Api for current user to update their location
	 * @param location
	 * @return
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/user/location", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateMyLocation(@RequestBody final Location location) throws ResourceNotFoundException{
		User user = getCurrentUser();
		User updated = userService.updateUserLocation(user, location);
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
}
