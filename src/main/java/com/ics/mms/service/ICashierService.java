/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Cashier;


/**
 * @author marco_000
 *
 */
public interface ICashierService extends IBaseService{
	
	public Result addCashier(Cashier cashier);
	
	public List<Cashier> listCashier();
	
	public Cashier getCashier(Integer id);
	
	public Result updateCashier(Cashier cashier);
	
	public Result removeCashier(Integer id);
}
