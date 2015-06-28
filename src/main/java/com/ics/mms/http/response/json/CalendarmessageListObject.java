package com.ics.mms.http.response.json;

public class CalendarmessageListObject {
	private String endTime;
	private String startTime;
	private String messageTitle;
	private String messageDetailer;
	private int messageStatus;
	private int mid;
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageDetailer() {
		return messageDetailer;
	}
	public void setMessageDetailer(String messageDetailer) {
		this.messageDetailer = messageDetailer;
	}
	public int getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(int messageStatus) {
		this.messageStatus = messageStatus;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
}
