package com.ics.mms.http.response.json;

public class FriendsListObject {
	private String userName;
	private int uid;
	private String openfireID;
	private String image;
	private int status;
	
	

	public String getuserName(){return userName;}
	public void setuserName(String userName){this.userName = userName;}

	public int getuid()	{return uid;}
	public void setuid(int uid){this.uid = uid;}
	
	public String getopenfireID(){return openfireID;}	
	public void setopenfireID(String openfireID){this.openfireID =openfireID;}
	
	public String getimage(){return image;}	
	public void setimage(String image){this.image =image;}
	
	public int getstatus()	{return status;}
	public void setstatus(int status){this.status = status;}
}
