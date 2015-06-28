/**
 * 
 */
package com.ics.mms.service.impl;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.mms.dao.IGenericDAO;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IItemService;
import com.mms.pojo.Item;


/**
 * @author prabhat
 *
 */
@Service
public class ItemService extends BaseService implements IItemService  {
	@Autowired
	IGenericDAO< Item > dao;
		
	@Override
	public Result addItem(Item item) {
		Result result = new Result();
		try {
			dao.setClazz( Item.class );
			dao.create(item);
			result.setResult(1);
			result.setMessage("Success");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			result.setResult(0);
			result.setMessage("Validation Error ! Please Check Input");
		}catch (org.hibernate.exception.ConstraintViolationException e){
			e.printStackTrace();
			result.setResult(0);
			result.setMessage("Duplicate Entry! Please Check Input");
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.setResult(0);
			result.setMessage("Exception");
		}
		return result;
	}

	@Override
	public List<Item> listItem() {
		List<Item> listItem = null;
		try {
			dao.setClazz( Item.class );
			listItem = dao.findAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		return listItem;
	}

	@Override
	public Result removeItem(Integer id) {
		int validate = 0;
		try {
			dao.setClazz( Item.class );
			dao.deleteById(id);
			validate = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			validate = 0;
		}
		return new Result(validate);
	}

	@Override
	public Item getItem(Integer id) {
		Item item = null;
		try {
			dao.setClazz( Item.class );
			item = dao.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	public Result updateItem(Item item) {
		Result result = new Result();
		try {
			dao.setClazz( Item.class );
			dao.update(item);
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
