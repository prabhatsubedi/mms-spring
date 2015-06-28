package com.ics.mms.context;
import org.hibernate.validator.constraints.NotEmpty;

public class MessageForm {
	@NotEmpty
	private String uid;
	@NotEmpty
	private String groupID;
	private String friendsID;

	public void setuid(String uid) {this.uid = uid;	}
	public String getuid() {return uid;	}
	
	public void setgroupID(String groupID) {this.groupID = groupID;}
	public String getgroupID() {return groupID;	}
	
	public void setfriendsID(String friendsID) {this.friendsID = friendsID;}
	public String getfriendsID() {return friendsID;}
}
