package com.flow.railwayservice.web.rest;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.OperationType;
import com.flow.railwayservice.dto.Platform;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TokenService;
import com.flow.railwayservice.service.UserService;
import com.flow.railwayservice.web.rest.vm.RestResponse;
import com.flow.railwayservice.web.rest.vm.SimpleRequest;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Api for current user to update their location
	 * @param location
	 * @return
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/users/location", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateMyLocation(@RequestBody final Location location) throws ResourceNotFoundException{
		User user = getCurrentUser();
		User updated = userService.updateUserLocation(user, location);
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
	
	/**
	 * Update the current user's device token and platform for push notifications
	 * @param simpleReq
	 * @param req
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/users/devicetoken", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateMyDeviceToken(@RequestBody final SimpleRequest simpleReq, HttpServletRequest req) throws ResourceNotFoundException{
		User user = getCurrentUser();
		String auth = req.getHeader("Authorization");
		String clientId = tokenService.decodeClientId(auth);
		Platform platform = getUserPlatform(clientId);
		User updated = userService.updateUserDeviceAndPlatform(user, simpleReq.getValue(), platform);
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
	
	/**
	 * Extract platform from client id
	 * @param clientId
	 * @return
	 */
	private Platform getUserPlatform(String clientId){
		switch(clientId){
		case "railwayservice-ios":
			return Platform.APNS;
		default:
			return Platform.WEB;
		}
	}
}
