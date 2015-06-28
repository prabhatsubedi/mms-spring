package com.ics.mms.http.response.json;

public class GroupsListObject {
	
	private int numMessage;
	private String groupDetail;
	private int groupID;
	private String groupName;
	
	public int getNumMessage() {
		return numMessage;
	}
	public void setNumMessage(int numMessage) {
		this.numMessage = numMessage;
	}
	public String getGroupDetail() {
		return groupDetail;
	}
	public void setGroupDetail(String groupDetail) {
		this.groupDetail = groupDetail;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
