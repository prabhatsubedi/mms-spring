package com.ics.mms.http.response.json;

public class PersonListObject {
	private String name;
	private int uid;
	private String image;
	private int unReadMsg;
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;	}
	
	public int getUid() {return uid;}
	public void setUid(int uid) {this.uid = uid;}
	
	public int getUnReadMsg() {	return unReadMsg;}
	public void setUnReadMsg(int unReadMsg) {this.unReadMsg = unReadMsg;}
	
	public String getImage() {return image;	}
	public void setImage(String image) {this.image = image;}
}
