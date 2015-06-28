/**
 * 
 */
package com.ics.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IPurchaseService;
import com.mms.pojo.Purchase;
import com.mms.pojo.PurchasedItems;


/**
 * @author prabhat
 *
 */
@Service
public class PurchaseService extends BaseService implements IPurchaseService  {
	@Autowired
	IGenericDAO< Purchase > genericDao;
	@Autowired
	IGenericDAO< PurchasedItems > genericDaoPurchasedItems;
	
	@Override
	public Result addPurchase(Purchase purchase) {
		Result result = new Result();
		try {
			genericDao.setClazz(Purchase.class);
			genericDao.create(purchase);
			System.out.println("Purchase ID: "+purchase.getPurchaseId());
			result.setResult(purchase.getPurchaseId());
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
	public Result addPurchasedItems(PurchasedItems purchasedItems) {
		Result result = new Result();
		
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			genericDaoPurchasedItems.create(purchasedItems);
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
	public List<Purchase> listPurchase() {
		List<Purchase> listPurchase = null;
		try {
			genericDao.setClazz(Purchase.class);
			listPurchase = genericDao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listPurchase;
	}

	@Override
	public Result removePurchase(Integer id) {
		int validate = 0;
		try {
			genericDao.setClazz(Purchase.class);
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
	public Purchase getPurchase(Integer id) {
		Purchase purchase = null;
		try {
			genericDao.setClazz(Purchase.class);
			purchase = genericDao.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return purchase;
	}

	@Override
	public Result updatePurchase(Purchase purchase) {
		Result result = new Result();
		try {
			genericDao.setClazz(Purchase.class);
			genericDao.update(purchase);
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
	public List<PurchasedItems> listPurchasedItems() {
		List<PurchasedItems> listPurchasedItems = null;
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			listPurchasedItems = genericDaoPurchasedItems.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listPurchasedItems;
	}

	@Override
	public List<PurchasedItems> listPurchasedItems(Integer id,	String idColumnName) {
		List<PurchasedItems> listPurchasedItems = null;
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			listPurchasedItems = genericDaoPurchasedItems.findAllById(id, idColumnName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listPurchasedItems;
	}

	@Override
	public Result updatePurchasedItems(PurchasedItems purchasedItems) {
		Result result = new Result();
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			genericDaoPurchasedItems.update(purchasedItems);
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
	public Result removePurchasedItem(Integer id) {
		int validate = 0;
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			genericDaoPurchasedItems.deleteById(id);
			validate = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			validate = 0;
		}
		return new Result(validate);
	}

	@Override
	public PurchasedItems getPurchasedItem(Integer id) {
		PurchasedItems purchasedItem = null;
		try {
			genericDaoPurchasedItems.setClazz(PurchasedItems.class);
			purchasedItem = genericDaoPurchasedItems.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return purchasedItem;
	}

}
