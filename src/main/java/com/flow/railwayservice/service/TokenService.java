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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.dto.ClientDetails;
import com.flow.railwayservice.web.rest.vm.SignupRequest;

@Service
public class TokenService {
	
	private static final String BASIC_AUTH = "Basic";
	
	@Inject
	private UserDetailsService userDetailsService;
	
	@Inject
	private TokenStore tokenStore;
	
	public OAuth2AccessToken grantNewTokenFromSignupRequest(SignupRequest req, String auth) throws Exception{
		String [] clientIdAndSecret = decodeClientIdAndSecret(auth);
		String clientId = clientIdAndSecret[0];
		UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	    for(GrantedAuthority ga : user.getAuthorities()){
	    	authorities.add(ga);
	    }
	    Map<String, String> requestParameters = new HashMap<>();
	    boolean approved = true;
	    ClientDetails details = new ClientDetails(clientId, authorities);

	    OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId,
	            details.getAuthorities(), approved, details.getScope(),
	            details.getResourceIds(), null, null, null);
	    
	    DefaultTokenServices defaultTokenServices = createDefaultTokenServices();
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	    OAuth2Authentication oauth = new OAuth2Authentication(oAuth2Request, authentication);
	    OAuth2AccessToken token = defaultTokenServices.createAccessToken(oauth);
	    return token;
	}
	
	private String[] decodeClientIdAndSecret(String authorization) {
		String values [] = new String[2];
		if (authorization != null && authorization.startsWith(BASIC_AUTH)) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring(BASIC_AUTH.length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			// credentials = username:password
			values = credentials.split(":", 2);
		}
		return values;
	}
	
	public String decodeClientId(String authorization){
		String values [] = new String[2];
		if (authorization != null && authorization.startsWith(BASIC_AUTH)) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring(BASIC_AUTH.length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			// credentials = username:password
			values = credentials.split(":", 2);
		}
		return values[0];
	}

     public DefaultTokenServices createDefaultTokenServices() throws Exception {
     	DefaultTokenServices tokenServices = new DefaultTokenServices();
     	tokenServices.setTokenStore(tokenStore);
     	// Disable refresh token
     	tokenServices.setSupportRefreshToken(true);
     	// Enabled infinte token validity
     	return tokenServices;
     }
}
