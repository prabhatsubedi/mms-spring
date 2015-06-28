package com.ics.mms.http.response.json;

public class LoginDataObject {
	
	private int result;
	private int uid;
	private String  openFireID;

	public int getresult()
	{
	    return result;
	}
	
	public void setresult(int result)
	{
	    this.result = result;
	}

	public int getuid()
	{
	    return uid;
	}
	public void setuid(int uid)
	{
	    this.uid = uid;
	}
	
	public String getopenFireID()
	{
	    return openFireID;
	}	
	public void setopenFireID(String openFireID)
	{
	    this.openFireID = openFireID;
	}
}
