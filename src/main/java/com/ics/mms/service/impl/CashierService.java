/**
 * 
 */
package com.ics.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.ICashierService;
import com.mms.pojo.Cashier;


/**
 * @author prabhat
 *
 */
@Service
public class CashierService extends BaseService implements ICashierService  {
	@Autowired
	IGenericDAO< Cashier > genericDao;
	
	@Override
	public Result addCashier(Cashier cashier) {
		Result result = new Result();
		
		try {
			genericDao.setClazz(Cashier.class);
			genericDao.create(cashier);
			result.setResult(1);
			result.setMessage("Success");
		} catch (org.hibernate.exception.ConstraintViolationException e){
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
	public List<Cashier> listCashier() {
		List<Cashier> listCashier = null;
		try {
			genericDao.setClazz(Cashier.class);
			listCashier = genericDao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listCashier;
	}

	@Override
	public Result removeCashier(Integer id) {
		int validate = 0;
		try {
			genericDao.setClazz(Cashier.class);
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
	public Cashier getCashier(Integer id) {
		Cashier cashier = null;
		try {
			genericDao.setClazz(Cashier.class);
			cashier = genericDao.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cashier;
	}

	@Override
	public Result updateCashier(Cashier cashier) {
		Result result = new Result();
		try {
			genericDao.setClazz(Cashier.class);
			genericDao.update(cashier);
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
