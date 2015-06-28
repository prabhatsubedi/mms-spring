package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Sales;
import com.mms.pojo.SoldItems;


public interface ISalesService extends IBaseService {
	
	Result addSales(Sales sale);
	
	List<Sales> listSales();
	
	Sales getSales(Integer id);
	
	Result updateSales(Sales sale);
	
	Result removeSales(Integer id);
	
	Result addSoldItems(SoldItems soldItems);
	
	SoldItems getSoldItem(Integer id);
	
	Result updateSoldItem(SoldItems soldItem);
	
	Result removeSoldItem(Integer id);
	
	List<SoldItems> listSoldItems();
	
	List<SoldItems> listSoldItems(Integer id, String idColumnName);
	
}
