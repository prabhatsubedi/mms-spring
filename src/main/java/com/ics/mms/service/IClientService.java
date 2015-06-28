package com.ics.mms.service;

import com.mms.pojo.Client;

public interface IClientService extends IBaseService {
	Client getClient(String clientName);
}
