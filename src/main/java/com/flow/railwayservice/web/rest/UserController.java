package com.flow.railwayservice.web.rest;


import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.OperationType;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TokenService;
import com.flow.railwayservice.service.UserService;
import com.flow.railwayservice.service.util.firebase.FirebaseAuthentication;
import com.flow.railwayservice.web.rest.vm.TokenPlatformRequest;
import com.flow.railwayservice.web.rest.vm.UpdatePasswordRequest;
import com.flow.railwayservice.web.rest.vm.UpdatePasswordResponse;
import com.flow.railwayservice.web.rest.vm.RestResponse;
import com.flow.railwayservice.web.rest.vm.SimpleRequest;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
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
	 * Update users preferences to recieve e-mail updates
	 * @param req
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value= "/users/emailupdates", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateEmailUpdatePreferences(@RequestBody final SimpleRequest req) throws ResourceNotFoundException{
		User user = getCurrentUser();
		User updated = userService.toggleReceiveEmailUpdates(user, (Boolean) req.getValue());
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
	
	/**
	 * Update users language key preference
	 * @param req
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/users/langkey", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateMyLanguageKey(@RequestBody final SimpleRequest req) throws ResourceNotFoundException{
		User user = getCurrentUser();
		User updated = userService.updateLanguageKey(user, (String)req.getValue());
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
	
    /**
     * Change user password
     * @param password
     * @return
     * @throws Exception 
     */
    @RequestMapping(path = "/users/password", method = RequestMethod.PUT)
    @ResponseBody
    public UpdatePasswordResponse changePassword(@RequestBody final UpdatePasswordRequest passwordRequest, HttpServletRequest req) throws Exception {
    	User user = getCurrentUser();
    	String token = req.getHeader("Authorization");
    	log.debug("Token is: {}", token);
        if (!checkPasswordLength(passwordRequest.getPassword())) {
            return new UpdatePasswordResponse(OperationType.NO_CHANGE);
        }
        boolean updated = userService.updatePassword(user, passwordRequest.getPassword());	
        // If password has successfully been updated, revoke token and issue a new one
        if(updated){
        		OAuth2AccessToken newToken = tokenService.grantNewUserAccessToken(user);
        		if(newToken != null){
                	tokenService.revokeToken(token);	// revoke prior token
            		return new UpdatePasswordResponse(OperationType.UPDATE, newToken);	// return new generated token
        		}
        }
        return new UpdatePasswordResponse(OperationType.NO_CHANGE);
    }
	
	/**
	 * Update the current user's device token and platform for push notifications
	 * @param simpleReq
	 * @param req
	 * @return
	 * @throws ResourceNotFoundException
	 */
;	@RequestMapping(value = "/users/resources/tokens", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<User> updateTokensAndPlatform(@RequestBody final TokenPlatformRequest req) throws ResourceNotFoundException{
		User user = getCurrentUser();
		User updated = userService.updateUserTokensAndPlatform(user, req.getDeviceToken(), req.getFcmToken(), req.getPlatform());
		return new RestResponse<User>(updated, OperationType.UPDATE);
	}
	
	/**
	 * Generate a token to access firebase database
	 * @return
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/users/resources/firebase", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> getFirebaseToken() throws ResourceNotFoundException{
		User user = getCurrentUser();
		String token = userService.getUserFirebaseToken(user);
		return new RestResponse<String>(token);
	}
}
