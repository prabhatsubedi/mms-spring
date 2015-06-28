/**
 * 
 */
package com.ics.mms.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IPartyService;
import com.mms.pojo.Party;


/**
 * @author prabhat
 *
 */

@Service
public class PartyService extends BaseService implements IPartyService  {
	@Autowired
	IGenericDAO< Party > genericDao;
		
	@Override
	public Result addParty(Party party) {
		Result result = new Result();
		try {
			genericDao.setClazz(Party.class);
			genericDao.create(party);
			result.setResult(1);
			result.setMessage("Success");
		}catch (org.hibernate.exception.ConstraintViolationException e){
			e.printStackTrace();
			result.setResult(0);
			result.setMessage("Duplicate Entry.");
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setResult(0);
			result.setMessage("Naming Exception");
		} 
		return result;
	}

	@Override
	public List<Party> listParty() {
		List<Party> listParty = null;
		try {
			genericDao.setClazz(Party.class);
			listParty = genericDao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listParty;
	}

	@Override
	public Result removeParty(Integer id) {
		int validate = 0;
		try {
			genericDao.setClazz(Party.class);
			genericDao.deleteById(id);
			validate = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			validate = 0;
		}
		return new Result(validate);
	}

	@Override
	public Party getParty(Integer id) {
		Party party = null;
		try {
			genericDao.setClazz(Party.class);
			party = genericDao.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return party;
	}

	@Override
	public Result updateParty(Party party) {
		Result result = new Result();
		try {
			genericDao.setClazz(Party.class);
			genericDao.update(party);
			result.setResult(1);
			result.setMessage("Success");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setResult(0);
			result.setMessage("Fail");
		}
		return result;
	}

}
