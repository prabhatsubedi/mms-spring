package com.ics.mms.http.response.json;

import java.util.List;

public class HomePageWithFriendjson {

	private List<FriendsListObject> friends;
	private List<GroupsListObject> group;
	private List<CalendarmessageListObject> calendar;
	private List<GroupmessageListObject> groupmessage;
	private PersonListObject person;
	

	private int numNewMeg;
	private int numMemo;
	private int numToDo;

	public int getnumNewMeg() {	return numNewMeg;	}
	public void setnumNewMeg(int numNewMeg) {this.numNewMeg = numNewMeg;}
	
	public int getnumMemo() {	return numMemo;	}
	public void setnumMemo(int numMemo) {this.numMemo = numMemo;}	
	
	public int getnumToDo() {	return numToDo;	}
	public void setnumToDo(int numToDo) {this.numToDo = numToDo;}	
	
	public List<FriendsListObject> getFriends() {
		return friends;
	}
	public void setFriends(List<FriendsListObject> friends) {
		this.friends = friends;
	}
	public List<GroupsListObject> getGroup() {
		return group;
	}
	public void setGroup(List<GroupsListObject> group) {
		this.group = group;
	}
	public List<CalendarmessageListObject> getCalendar() {
		return calendar;
	}
	public void setCalendar(List<CalendarmessageListObject> calendar) {
		this.calendar = calendar;
	}
	public List<GroupmessageListObject> getGroupmessage() {
		return groupmessage;
	}
	public void setGroupmessage(List<GroupmessageListObject> groupmessage) {
		this.groupmessage = groupmessage;
	}
	public PersonListObject getPerson() {
		return person;
	}
	public void setPerson(PersonListObject person) {
		this.person = person;
	}
	
	
	
	
//	@Override
//	public String toString() {
//		return "HomePageWithFriendjson [friends=" + friends + ", group="
//				+ group + ", calendar=" + calendar + ", groupmessage="
//				+ groupmessage + ", person=" + person + "]";
//	}


}

