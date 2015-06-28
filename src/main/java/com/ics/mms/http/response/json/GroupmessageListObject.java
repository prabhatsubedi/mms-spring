package com.ics.mms.http.response.json;

public class GroupmessageListObject {
	private String fromUserName;
	private String message;
	private String createTime;
	private int groupID;
	private int mid;
	private int fromUserID;
	private String image;
	
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(int fromUserID) {
		this.fromUserID = fromUserID;
	}
	public String getimage() {
		return image;
	}
	public void setimage(String image) {
		this.image = image;
	}
}
