package com.flow.railwayservice.service.mapper;

import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.service.dto.User;
import com.flow.railwayservice.web.rest.vm.UserDTO;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and Dto
 */
public class UserMapper {
	
	private LocationMapper locationMapper = new LocationMapper();

	/**
	 * To entity user
	 * @param u
	 * @return
	 */
    public RUser toRUser(User u){
    	RUser ru = null;
    	
    	if( u != null) {
    		ru = new RUser();
    		ru.setId(u.getId());
    		ru.setPassword(u.getPassword());
    		ru.setLogin(u.getLogin());
    		ru.setEmail(u.getEmail());
    		ru.setRole(u.getRole());
    		ru.setName(u.getName());
    		ru.setActivated(u.isActivated());
    		//TODO: set location
    	}
    	
    	return ru;
    }
    
    /**
     * To User DTO
     * @param ru
     * @return
     */
    public User toUser(RUser ru){
    	User u = null;
    	
    	if(ru != null){
    		u = new User();
    		u.setId(ru.getId());
    		u.setActivated(ru.getActivated());
    		u.setEmail(ru.getEmail());
    		u.setLogin(ru.getLogin());
    		u.setPassword(ru.getPassword());
    		u.setName(ru.getName());
    		u.setRole(ru.getRole());	
    		//TODO: set location
    	}
    	
    	return u;
    }
}
