package com.ics.mms.service.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ics.mms.service.IClientService;
import com.mms.pojo.Client;
import com.mms.pojo.PurchasedItems;


public class ClientService extends BaseService implements IClientService {

	@Override
	public Client getClient(String clientName) {
		Client client = null;
		/*try {
			ic = new InitialContext();
			IGenericDao<Client> genericDao = (IGenericDao<Client>) ic.lookup("java:comp/GenericDao");
			genericDao.setClazz(Client.class);
			client = genericDao.findById(clientName, "clientName");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
		}*/
		return client;
	}

}
