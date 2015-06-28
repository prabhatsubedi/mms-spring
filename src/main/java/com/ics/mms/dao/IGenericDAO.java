package com.ics.mms.dao;

import java.io.Serializable;
import java.util.List;

import com.mms.pojo.User;

public interface IGenericDAO<T extends Serializable>{
	
	void setClazz( Class< T > clazzToSet );
	
	User checkLogin(String username, String password);
	
	T findOne( Integer id );
	
	List< T > findAll();
 
	List< T > findAllById(Object columnValue, String columnName);
	
	T findByColumn(Object columnValue, String columnName);
	
	void create( T entity );
  
	void update( T entity );
 
	void delete( T entity );
	
	void deleteById( Integer entityId );
	
	T findByQuery(String query);
}
