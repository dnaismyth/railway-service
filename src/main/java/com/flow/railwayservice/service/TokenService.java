package com.flow.railwayservice.service;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.web.rest.vm.SignupRequest;

@Service
public class TokenService {
	
	private static final String BASIC_AUTH = "Basic";
	
	@Inject
	private AuthorizationServerTokenServices defaultTokenServices;
	
	@Inject
	private UserDetailsService userDetailsService;
	
	public OAuth2AccessToken grantNewTokenFromSignupRequest(SignupRequest req, String auth){
		
		UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	    for(GrantedAuthority ga : user.getAuthorities()){
	    	authorities.add(ga);
	    }
	    validateClientId(auth);
	    Map<String, String> requestParameters = new HashMap<>();
	    String clientId = "acme";	//TODO: Get client id
	    boolean approved = true;
	    Set<String> scope = new HashSet<>();
	    scope.add("read");		// set scopes from client id
	    scope.add("write");
	    Set<String> resourceIds = new HashSet<>();
	    Set<String> responseTypes = new HashSet<>();
	    responseTypes.add("code");
	    Map<String, Serializable> extensionProperties = new HashMap<>();

	    OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId,
	            authorities, approved, scope,
	            resourceIds, null, responseTypes, extensionProperties);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	    OAuth2Authentication oauth = new OAuth2Authentication(oAuth2Request, authentication);
	    OAuth2AccessToken token = defaultTokenServices.createAccessToken(oauth);
	    return token;
	}
	
	private void validateClientId(String authorization) {
		if (authorization != null && authorization.startsWith(BASIC_AUTH)) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring(BASIC_AUTH.length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			if(values.length >= 2){
				
			}
		}
	}
}
