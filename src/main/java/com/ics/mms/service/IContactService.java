/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.GetContactResJson;
import com.ics.mms.http.response.json.Result;


/**
 * @author marco_000
 *
 */
public interface IContactService extends IBaseService{
	
	/**
	 * Add Contact Service
	 * @param String firstName
	 * @param String lastName
	 * @param String telephone
	 * @param String email
	 * @return Result
	 */
	public Result addContact(String firstName, String lastName, String telephone, String email);
	
	/**
	 * Get Contact Service
	 * @return list
	 */
	public List<GetContactResJson> listContact();
	
	
	
	/**
	 * Delete Contact Service
	 * @param int id
	 * @return Result
	 */
	public Result removeContact(Integer id);
	
	
	
}
