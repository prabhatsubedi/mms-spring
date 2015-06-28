package com.ics.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.ISalesService;
import com.mms.pojo.Sales;
import com.mms.pojo.SoldItems;


@Service
public class SalesService extends BaseService implements ISalesService{

	@Autowired
	IGenericDAO< Sales > genericDao;
	@Autowired
	IGenericDAO< SoldItems > genericDaoSoldItems;
	
	@Override
	public Result addSales(Sales sale) {
		Result result = new Result();
		try {
			//ic = new InitialContext();
			//genericDao = (IGenericDao<Sales>) ic.lookup("java:comp/GenericDao");
			genericDao.setClazz(Sales.class);
			genericDao.create(sale);
			System.out.println("Sales ID: "+sale.getSalesId());
			result.setResult(sale.getSalesId());
			result.setMessage("Success");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setResult(0);
			result.setMessage("Fail");
		}
		return result;
	}

	@Override
	public List<Sales> listSales() {
		List<Sales> listSales = null;
		try {
			genericDao.setClazz(Sales.class);
			listSales = genericDao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listSales;
	}

	@Override
	public Sales getSales(Integer id) {
		Sales Sales = null;
		try {
			genericDao.setClazz(Sales.class);
			Sales = genericDao.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Sales;
	}

	@Override
	public Result updateSales(Sales sale) {
		Result result = new Result();
		try {
			genericDao.setClazz(Sales.class);
			genericDao.update(sale);
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

	@Override
	public Result removeSales(Integer id) {
		int validate = 0;
		try {
			genericDao.setClazz(Sales.class);
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
	public Result addSoldItems(SoldItems soldItems) {
		Result result = new Result();
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			genericDaoSoldItems.create(soldItems);
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

	@Override
	public List<SoldItems> listSoldItems() {
		List<SoldItems> listSoldItems = null;
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			listSoldItems = genericDaoSoldItems.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listSoldItems;
	}

	@Override
	public List<SoldItems> listSoldItems(Integer id, String idColumnName) {
		List<SoldItems> listSoldItems = null;
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			listSoldItems = genericDaoSoldItems.findAllById(id, idColumnName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listSoldItems;
	}

	@Override
	public SoldItems getSoldItem(Integer id) {
		SoldItems soldItem = null;
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			soldItem = genericDaoSoldItems.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return soldItem;
	}

	@Override
	public Result updateSoldItem(SoldItems soldItem) {
		Result result = new Result();
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			genericDaoSoldItems.update(soldItem);
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

	@Override
	public Result removeSoldItem(Integer id) {
		int validate = 0;
		try {
			genericDaoSoldItems.setClazz(SoldItems.class);
			genericDaoSoldItems.deleteById(id);
			validate = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			validate = 0;
		}
		return new Result(validate);
	}

}
