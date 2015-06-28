/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Purchase;
import com.mms.pojo.PurchasedItems;


/**
 * @author marco_000
 *
 */
public interface IPurchaseService extends IBaseService{
	
	public Result addPurchase(Purchase purchase);
	
	public List<Purchase> listPurchase();
	
	public Purchase getPurchase(Integer id);
	
	public Result updatePurchase(Purchase purchase);
	
	public Result removePurchase(Integer id);
	
	public Result addPurchasedItems(PurchasedItems purchasedItems);
	
	PurchasedItems getPurchasedItem(Integer id);
	
	public Result updatePurchasedItems(PurchasedItems purchasedItems);
	
	Result removePurchasedItem(Integer id);
	
	public List<PurchasedItems> listPurchasedItems();
	
	public List<PurchasedItems> listPurchasedItems(Integer id, String idColumnName);
}
