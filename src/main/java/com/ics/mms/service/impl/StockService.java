/**
 * 
 */
package com.ics.mms.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IStockService;
import com.mms.pojo.Stock;


/**
 * @author prabhat
 *
 */
@Service
public class StockService extends BaseService implements IStockService  {
	@Autowired
	IGenericDAO< Stock > genericDao;
	 	
	@Override
	public Result addStock(Stock stock) {
		Result result = new Result();
		try {
			genericDao.setClazz(Stock.class);
			genericDao.create(stock);
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
	public Stock getStock(Integer itemId, String batchNo) {
		Stock stock = null;
		String query = "WHERE itemId = "+itemId+" AND batchNo = '"+batchNo+"'";
		System.out.println("Query: "+query);
		try {
			genericDao.setClazz(Stock.class);
			stock = (Stock) genericDao.findByQuery(query);
		} catch (Exception e1) {
			logger.error("Stock Exception........");
		}
		return stock;
	}
	
	@Override
	public List<Stock> listStock() {
		List<Stock> listStock = null;
		try {
			genericDao.setClazz(Stock.class);
			listStock = genericDao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listStock;
	}

	@Override
	public List<Stock> listStockByItem(Integer itemId) {
		List<Stock> listStock = null;
		try {
			genericDao.setClazz(Stock.class);
			listStock = genericDao.findAllById(itemId, "itemId");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listStock;
	}
	
	@Override
	public Result updateStock(Stock stock) {
		Result result = new Result();
		try {
			genericDao.setClazz(Stock.class);
			genericDao.update(stock);
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
