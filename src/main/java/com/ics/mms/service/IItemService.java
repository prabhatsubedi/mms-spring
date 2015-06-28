/**
 * 
 */
package com.ics.mms.service;

import java.util.List;

import com.ics.mms.http.response.json.GetContactResJson;
import com.ics.mms.http.response.json.Result;
import com.mms.pojo.Item;


/**
 * @author marco_000
 *
 */
public interface IItemService extends IBaseService{
	
	public Result addItem(Item item);
	
	public List<Item> listItem();
	
	public Item getItem(Integer id);
	
	public Result updateItem(Item item);
	
	public Result removeItem(Integer id);
}
