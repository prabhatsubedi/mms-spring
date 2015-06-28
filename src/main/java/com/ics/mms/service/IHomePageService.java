package com.ics.mms.service;

import org.apache.http.client.HttpClient;

import com.ics.mms.http.response.json.HomePageWithFriendjson;


public interface IHomePageService extends IBaseService {

	public HomePageWithFriendjson getHPFriendList(HttpClient httpclient, Integer uid);
}
