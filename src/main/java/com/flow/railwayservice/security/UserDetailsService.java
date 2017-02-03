package com.flow.railwayservice.security;

import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.dto.UserRole;
import com.flow.railwayservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<RUser> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = getUserAuthorities(userFromDatabase.get());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }
    
    /**
     * Map authorities
     * @param ru
     * @return
     */
    public List<GrantedAuthority> getUserAuthorities(RUser ru){
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	switch(ru.getRole()){
    	case ADMIN:
    		authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
    		break;
    	case USER:
    		authorities.add(new SimpleGrantedAuthority(UserRole.USER.name()));
    		break;
    	case GUEST:
    		authorities.add(new SimpleGrantedAuthority(UserRole.GUEST.name()));
    		break;
    	default:
    		break;
    	}
    	
    	return authorities;
    }
}
