package com.ics.mms.service.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ics.mms.http.request.json.GetHomePageFriendListJson;
import com.ics.mms.http.response.json.HomePageWithFriendjson;
import com.ics.mms.service.IHomePageService;


public class HomePageService extends BaseService implements IHomePageService {

	@Autowired
	WebServiceClientService webServiceClientService;
	
	public HomePageWithFriendjson getHPFriendList(HttpClient httpclient, Integer uid){
		
		HomePageWithFriendjson list2 = null;
		
		logger.info("getHPFriendList() begin");
		logger.info(uid);
		
		
		if(uid == null)
			return null;
		
		GetHomePageFriendListJson json = new GetHomePageFriendListJson();
		json.setUid(uid);	

		

		
		HttpResponse response = webServiceClientService.request(httpclient, webServiceConstant.request_type_homePageWithFriend,  json);
        
		if(response==null)
			return null;

		logger.debug("StatusCode=" + response.getStatusLine().getStatusCode());
        
        if(response.getStatusLine().getStatusCode() != 200 ){
        	logger.error("StatusCode:" + response.getStatusLine().getStatusCode());
        	return null;
        }
        
        logger.debug("StatusCode:" + response.getStatusLine().getStatusCode());
        
        
        HttpEntity entity = response.getEntity();
        
        
        if (entity != null) {
        	ObjectMapper objectMapper2 = new ObjectMapper();
            try {
				list2 = objectMapper2.readValue(EntityUtils.toString(entity), HomePageWithFriendjson.class);
			} catch (JsonParseException e) {
				logger.error("JsonParseException", e);
			} catch (JsonMappingException e) {
				logger.error("JsonMappingException", e);
			} catch (ParseException e) {
				logger.error("ParseException", e);
			} catch (IOException e) {
				logger.error("IOException", e);
			} 

            
           
        }
        
        logger.info("getHPFriendList() end");
		return list2;
	}
}
