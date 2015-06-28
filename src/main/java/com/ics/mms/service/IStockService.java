/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Stock;


public interface IStockService extends IBaseService{
	
	public Result addStock(Stock stock);
	
	public Stock getStock(Integer itemId, String batch);
	
	public List<Stock> listStock();
	
	public List<Stock> listStockByItem(Integer itemId);
	
	public Result updateStock(Stock stock);
}
