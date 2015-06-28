/**
 * 
 */
package com.ics.mms.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.ics.mms.http.request.json.AddContactReqJson;
import com.ics.mms.http.request.json.DeleteContactReqJson;
import com.ics.mms.http.request.json.GetContactReqJson;
import com.ics.mms.http.response.json.GetContactResJson;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IContactService;


/**
 * @author marco_000
 *
 */
public class ContactService extends BaseService implements IContactService  {

	@Autowired
	WebServiceClientService webServiceClientService;
	
	@Override
	public Result addContact(String firstName, String lastName, String telephone, String email) {
		
		logger.info("addContact() begin");
		
		// Optional parameters
		if(firstName == null || lastName == null || telephone == null || email == null){
			// logger.error("Missing parameter(s)");
			// return new Result(0,"failed");
		}
		
		Result result = null;
		
		AddContactReqJson reqJson = new AddContactReqJson();
		reqJson.setFirstName(firstName);
		reqJson.setLastName(lastName);
		reqJson.setTelephone(telephone);
		reqJson.setEmail(email);
		
		HttpResponse response = webServiceClientService.requestREST(null, reqJson, appContext.getWebServiceHostUrl() + webServiceConstant.request_uri_addContact);

		logger.debug("StatusCode=" + response.getStatusLine().getStatusCode());
        
        if(response == null || response.getStatusLine().getStatusCode() != 200 ){
        	logger.error("StatusCode:" + response.getStatusLine().getStatusCode());
        	return new Result(0,"failed");
        }        

        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
        	ObjectMapper objectMapper = new ObjectMapper();
            try {
            	result = objectMapper.readValue(EntityUtils.toString(entity), Result.class);            	
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
        
        logger.info("addContact() end");
		return result == null ? new Result(0,"failed") : result;
	}

	@Override
	public List<GetContactResJson> listContact() {
		logger.info("listContact() begin");
		
		GetContactReqJson reqJson = new GetContactReqJson();
		List<GetContactResJson> result = null;
		
		HttpResponse response = webServiceClientService.requestREST(null, reqJson, appContext.getWebServiceHostUrl() + webServiceConstant.request_uri_getContact);

		logger.debug("StatusCode=" + response.getStatusLine().getStatusCode());
        
        if(response == null || response.getStatusLine().getStatusCode() != 200 ){
        	logger.error("StatusCode:" + response.getStatusLine().getStatusCode());
        	return null;	
        }		       
        
        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
        	ObjectMapper objectMapper = new ObjectMapper();
            try {
            	result = objectMapper.readValue(EntityUtils.toString(entity), new TypeReference<List<GetContactResJson>>() {});             	
            	Collections.reverse(result);
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
        
        logger.info("listContact() end");
		return result;
	}

	@Override
	public Result removeContact(Integer id) {
		logger.info("removeContact() begin");

		if(id == null){
			logger.error("Missing parameter(s)");
			return new Result(0,"failed");
		}
		
		Result result = null;
		
		DeleteContactReqJson reqJson = new DeleteContactReqJson();
		reqJson.setId(id);
		
		HttpResponse response = webServiceClientService.requestREST(null, reqJson, appContext.getWebServiceHostUrl() + webServiceConstant.request_uri_deleteContact);

		logger.debug("StatusCode=" + response.getStatusLine().getStatusCode());
        
        if(response == null || response.getStatusLine().getStatusCode() != 200 ){
        	logger.error("StatusCode:" + response.getStatusLine().getStatusCode());
        	return new Result(0,"failed");
        }        

        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
        	ObjectMapper objectMapper = new ObjectMapper();
            try {
            	result = objectMapper.readValue(EntityUtils.toString(entity), Result.class);            	
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
        
        logger.info("removeContact() end");
		return result == null ? new Result(0,"failed") : result;
	}
	


}
