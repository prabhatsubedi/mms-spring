package com.ics.mms.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

public interface IWebServiceClientService  extends IBaseService{

	public HttpResponse requestByString(HttpClient httpclient, String json);
	public HttpResponse request(HttpClient httpclient, String requestType, Object jsonObject);
	public HttpResponse requestREST( Object o, String url);
	public HttpResponse requestREST( Object o, String url, String attachment);
	
	public HttpResponse requestREST(HttpClient httpclient, Object o, String url) ;
	public HttpResponse requestREST(HttpClient httpclient, Object o, String url, String attachment) ;
}
