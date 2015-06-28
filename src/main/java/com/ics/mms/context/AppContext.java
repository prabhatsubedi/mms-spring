package com.ics.mms.context;

public class AppContext {

	private String testStr;
	private String webServiceHostUrl;
	private Integer webServiceTimeout;
	private String contextRoot;
	private String springRoot;

	
	public String getContextRoot() {
		return contextRoot;
	}

	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}

	public String getSpringRoot() {
		return springRoot;
	}

	public void setSpringRoot(String springRoot) {
		this.springRoot = springRoot;
	}

	public Integer getWebServiceTimeout() {
		return webServiceTimeout;
	}

	public void setWebServiceTimeout(Integer webServiceTimeout) {
		this.webServiceTimeout = webServiceTimeout;
	}

	public String getWebServiceHostUrl() {
		return webServiceHostUrl;
	}

	public void setWebServiceHostUrl(String webServiceHostUrl) {
		this.webServiceHostUrl = webServiceHostUrl;
	}

	public String getTestStr() {
		return testStr;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}
	
	
}
