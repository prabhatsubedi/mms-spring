package com.ics.mms.constant;

public class WebServiceConstant {


	/*
	 */	
	public final String request_type_login = "login";
	public final String request_type_homePageWithFriend = "homePageWithFriend";
	public final String request_type_viewPostListByGroup = "viewPostListByGroup";
	public final String request_type_viewMessage = "viewMessage";
	public final String request_type_getSchoolList = "getSchoolList";
	public final String request_type_getGroupList = "getGroupList";
	public final String request_type_createMessage = "createMessage";
	
	/* No found contact request type on web service. use request URI to call web service */
	public final String request_uri_getContact = "/../contact/get";
	public final String request_uri_addContact = "/../contact/add";
	public final String request_uri_deleteContact = "/../contact/delete";

}
