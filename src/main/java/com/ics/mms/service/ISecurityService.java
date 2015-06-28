package com.ics.mms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.ics.mms.http.request.json.GetHomePageFriendListJson;
import com.ics.mms.http.response.json.LoginDataObject;



public interface ISecurityService  extends IBaseService{

	public Integer getUserId();
	
	public LoginDataObject login(HttpServletRequest request, String username, String password);

	public LoginDataObject login(HttpClient httpclient, HttpServletRequest request, String username, String password);
		
	
	public void logout(HttpServletRequest request);
	
}
