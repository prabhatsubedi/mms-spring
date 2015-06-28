package com.ics.mms.http.request.json;

public class BaseRequestJson {

	private String requestType;
	private Object requestData;

	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Object getRequestData() {
		return requestData;
	}
	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}
	
	
}
