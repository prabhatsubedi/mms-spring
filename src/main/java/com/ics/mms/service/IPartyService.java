/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Party;


/**
 * @author marco_000
 *
 */
public interface IPartyService extends IBaseService{
	
	public Result addParty(Party party);
	
	public List<Party> listParty();
	
	public Party getParty(Integer id);
	
	public Result updateParty(Party party);
	
	public Result removeParty(Integer id);
}
