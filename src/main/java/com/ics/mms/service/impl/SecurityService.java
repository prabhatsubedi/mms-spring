package com.ics.mms.service.impl;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.LoginDataObject;
import com.ics.mms.security.AuthenticationManager;
import com.ics.mms.service.ISecurityService;
import com.ics.mms.service.IWebServiceClientService;
import com.mms.pojo.Item;
import com.mms.pojo.User;


@Service
public class SecurityService extends BaseService implements ISecurityService {
	
	IGenericDAO< User > genericDAO;
	 
	@Autowired
	public void setDao( IGenericDAO< User > daoToSet ){
		genericDAO = daoToSet;
		genericDAO.setClazz( User.class );
	}
	@Autowired
	protected IWebServiceClientService webServiceClientService;
	@Autowired
	protected AuthenticationManager authenticationProvider; 
	
	public Integer getUserId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null){
			logger.debug("getUserId ===== " + auth.getPrincipal().toString());
			return Integer.valueOf(auth.getName());
		}
			
		else{
			logger.debug("auth is null");
			return null;
		}
			
	}
	
	public LoginDataObject login(HttpServletRequest request, String username, String password){
		HttpClient httpclient = new DefaultHttpClient();
		return login(httpclient, request, username , password);
	}
	public LoginDataObject login(HttpClient httpclient, HttpServletRequest request, String username, String password){
		
		logger.info("login() begin");
		User user = null;
		boolean flag = false;
		try {
			user = genericDAO.checkLogin(username, password);
			if(user != null && username.equals(user.getLoginname())){
				System.out.println("After Login: "+user.getFirstname());
				flag = true;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(flag == false || username == null || password == null){
			logger.error("Missing parameter(s)");
			return null;
		}

		LoginDataObject result = new LoginDataObject();
		
		HashMap<String,Object> paramList = new HashMap<String,Object>();
		
		paramList.put("userName", username);
		paramList.put("password", password);

        if (flag != false) {
             try {
				result.setresult(1);
				result.setuid(user.getId());
				
				if(request != null){
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(result.getuid(), password);
					token.setDetails(new WebAuthenticationDetails(request));
					
					Authentication authentication = new UsernamePasswordAuthenticationToken(result.getuid(), password, authenticationProvider.AUTHORITIES);

			        logger.debug("Logging in with user id " + authentication.getPrincipal().toString());
			        SecurityContextHolder.getContext().setAuthentication(authentication);
				}
             }  
			 catch (ParseException e) {
				logger.error("error", e);
			} 
        }
        logger.info("login() end");
		return result;
	}
	
	public void logout(HttpServletRequest request){
		logger.info("logout() begin");
		
		SecurityContextHolder.clearContext();

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		logger.info("logout() end");
	}
}
