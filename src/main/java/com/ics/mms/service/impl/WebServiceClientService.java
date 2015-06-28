package com.ics.mms.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ics.mms.http.request.json.BaseRequestJson;
import com.ics.mms.service.IWebServiceClientService;
import com.ics.mms.utility.FileSystemUtils;


@Service
public class WebServiceClientService extends BaseService implements IWebServiceClientService {


	
	public HttpResponse request(HttpClient httpclient, String requestType, Object jsonObject) {
		if(requestType == null || jsonObject == null){
			logger.warn("Missing parameter(s)");
			return null;
		}

		if(jsonObject instanceof HashMap<?, ?>){
			HashMap<String, Object> tmp = (HashMap<String,Object>) jsonObject;

			StringBuffer sb = new StringBuffer("{\"requestType\":\"");
	    	
	    	sb.append(requestType);
	    	sb.append("\",\"requestData\":{");
	    	
	    	Iterator<String> it =tmp.keySet().iterator();  
	    	
	    	boolean needSeperator = true;
	    	
	    	while (it.hasNext()) {  
	    	      String key = it.next();  

	    	      if(tmp.get(key) == null)
	    	    	  continue;

	    	      
	    	      sb.append("\"");
	    	      sb.append(key);
	    	      sb.append("\":\"");
	    	      sb.append(tmp.get(key).toString());
	    	      sb.append("\"");
	    	      
	    	      if(needSeperator && it.hasNext()){
	    	    	  sb.append(",");
	    	      }
	    	}  
	    	
	    	sb.append("}}");
	    	return requestByString(httpclient,sb.toString());
		}
			
		
		Gson gson = new Gson();
		
		BaseRequestJson base = new BaseRequestJson();
		base.setRequestType(requestType);
		base.setRequestData(jsonObject);
		
		String json = gson.toJson(base);
		
		return requestByString(httpclient,json);
	}
	
	public HttpResponse requestByString(HttpClient httpclient, String json) {
		logger.info("requestByString() begin");
		logger.info("json = " + json);

		
   
		if(httpclient == null)
			httpclient = new DefaultHttpClient();
    	
		HttpParams httpParams = httpclient.getParams();

		HttpConnectionParams.setConnectionTimeout( httpParams, appContext.getWebServiceTimeout());

		HttpConnectionParams.setSoTimeout(httpParams, appContext.getWebServiceTimeout());

		HttpPost post = new HttpPost(appContext.getWebServiceHostUrl());

    	post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	
    	//Construct request json
    	
    	

    	params.add(new BasicNameValuePair("json", json));
    	
    	HttpResponse response = null;
    	

		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = httpclient.execute(post);
		} catch (UnsupportedEncodingException e) {
			logger.error("error", e);
		} catch (ClientProtocolException e) {
			logger.error("error", e);
		} catch (IOException e) {
			logger.error("error", e);
		}


    	logger.info("requestByString() end");
    	return response;
	}

	public HttpResponse requestREST( Object o, String url) {
		return requestREST(null,o,url,null);
	}
	public HttpResponse requestREST( Object o, String url, String attachment_path) {
		return requestREST(null,o,url,attachment_path);
	}
	public HttpResponse requestREST(HttpClient httpclient, Object o, String url) {
		return requestREST(httpclient,o,url,null);
	}
	public HttpResponse requestREST(HttpClient httpclient, Object o, String url, String attachment_path) {
		logger.info("requestREST() begin");
		logger.info("object = " + o);
		logger.info("url = " + url);
		logger.info("attachment = " + attachment_path);

		if(o == null || url == null)
			return null;

		if(httpclient == null)
			httpclient = new DefaultHttpClient();
    	
		Gson gson = new Gson();
		String json = gson.toJson(o);
		
		HttpParams httpParams = httpclient.getParams();

		HttpConnectionParams.setConnectionTimeout( httpParams, appContext.getWebServiceTimeout());

		HttpConnectionParams.setSoTimeout(httpParams, appContext.getWebServiceTimeout());

		HttpPost post = new HttpPost(url);

    	post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	
    	//Construct request json
    	
    	

    	params.add(new BasicNameValuePair("json", json));
    	
    	
    	
    	HttpResponse response = null;
    	

    	
		try {
			if(attachment_path!=null){
				MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.addPart("json", new StringBody(json));
				
				File fileToUpload = new File(attachment_path);
				FileBody fileBody = new FileBody(fileToUpload, "application/octet-stream");
				entity.addPart("attach", fileBody);
				
				post.setEntity(entity);
				/*MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
				parts.add("json", json);
				Resource logo = new ClassPathResource("/org/springframework/http/converter/logo.jpg");
				parts.add("logo", logo);*/
				
			/*	parts.add("attach", attachment.transferTo(arg0))*/
				
				
			}else{
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}
			response = httpclient.execute(post);
		} catch (UnsupportedEncodingException e) {
			logger.error("error", e);
		} catch (ClientProtocolException e) {
			logger.error("error", e);
		} catch (IOException e) {
			logger.error("error", e);
		}


    	logger.info("requestREST() end");
    	return response;
	}


}
