package com.ics.mms.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ics.mms.http.response.json.LoginDataObject;
import com.ics.mms.service.ISecurityService;



public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationProvider{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	static {
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Autowired
	ISecurityService securityService;
	
    @Override
    public boolean supports(Class<? extends Object> authentication) {
    	return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("authenticate() begin");
		logger.debug("name = " + auth.getName());

		LoginDataObject login = securityService.login(null, auth.getName(), (String) auth.getCredentials());

		if(login == null)
			throw new BadCredentialsException("Bad Credentials");
		
		if(login.getresult() != 1)
			throw new BadCredentialsException("Bad Credentials");
		
		
		//if (auth.getName().equals(auth.getCredentials())) {
			return new UsernamePasswordAuthenticationToken(login.getuid(), auth.getCredentials(), AUTHORITIES);
		//}
		//throw new BadCredentialsException("Bad Credentials");
	}

}
