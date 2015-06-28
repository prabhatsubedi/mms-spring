package com.ics.mms.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mms.pojo.User;

public abstract class AbstractDAO< T extends Serializable > {
	
	private Class< T > clazz;
	
    @Autowired
    protected SessionFactory sessionFactory;

    protected final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
	
	public void setClazz( Class< T > clazzToSet ){
		this.clazz = clazzToSet;
	}
	@Transactional
	public User checkLogin(String loginname, String password){
		System.out.println("Login Name: "+loginname);
		Query q =  getCurrentSession().createQuery("SELECT u FROM User u WHERE u.loginname = :login AND u.password = :pass");
        q.setParameter("login", loginname);
        q.setParameter("pass", password);
        User user = null;
        try{
        	 user = (User) q.uniqueResult(); 
        }catch(Exception ex){
        	System.out.println("No User Found");
        	System.out.println("Exception: "+ex.getMessage());
        }
        return user;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public T findOne( Integer id ){
		return (T) getCurrentSession().get(clazz, id);
		//http://www.baeldung.com/2011/12/08/simplifying-the-data-access-layer-with-spring-and-java-generics/
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List< T > findAll(){
		System.out.println("Class: "+clazz.getName());
		return getCurrentSession().createQuery( "from "+clazz.getName()).list();
	}
 
	@SuppressWarnings("unchecked")
	@Transactional
	public List< T > findAllById(Object columnValue, String columnName){
		return getCurrentSession().createQuery( "from " + clazz.getName() +" where "+columnName+" = "+columnValue).list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public T findByColumn(Object columnValue, String columnName){
		System.out.println("Column Name: "+columnName+" Column Value: "+columnValue);
		return (T) getCurrentSession().createQuery( "from " + clazz.getName() +" where "+columnName+" = "+"'"+columnValue+"'").uniqueResult();
	}
	
	@Transactional
	public void create( T entity ){
		Transaction tx = null;
		try{
			tx = getCurrentSession().beginTransaction();
			tx.setTimeout(5);
			
			getCurrentSession().persist( entity );
			
			//tx.commit();
		}catch(RuntimeException e){
    		try{
    			tx.rollback();
    			System.err.println("Roll Back Transaction "+e);
    		}catch(RuntimeException rbe){
    			System.err.println("Couldn�t roll back transaction "+rbe);
    		}
    		throw e;
    	}catch(Exception e){
    		try{
    			tx.rollback();
    			System.err.println("Roll Back Transaction "+e);
    		}catch(RuntimeException rbe){
    			System.err.println("Couldn�t roll back transaction "+rbe);
    		}
    		throw e;
    	}
	}
 
	@Transactional
	public void update( T entity ){
		Transaction tx = null;
		try{
			tx = getCurrentSession().beginTransaction();
			tx.setTimeout(5);
			
			getCurrentSession().merge( entity );
			
			//tx.commit();
		}catch(RuntimeException e){
    		try{
    			tx.rollback();
    			System.err.println("Roll Back Transaction "+e);
    		}catch(RuntimeException rbe){
    			System.err.println("Couldn�t roll back transaction "+rbe);
    		}
    		throw e;
    	}
	}
 
	@Transactional
	public void delete( T entity ){
		Transaction tx = null;
		try{
			tx = getCurrentSession().beginTransaction();
			tx.setTimeout(5);
			
			getCurrentSession().delete(entity );
			
			tx.commit();
		}catch(RuntimeException e){
    		try{
    			tx.rollback();
    			System.err.println("Roll Back Transaction "+e);
    		}catch(RuntimeException rbe){
    			System.err.println("Couldn�t roll back transaction "+rbe);
    		}
    		throw e;
    	}
	}
	
	@Transactional
	public void deleteById( Integer entityId ){
		T entity = findOne( entityId );
		delete(entity);
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public T findByQuery(String query){
		return (T) getCurrentSession().createQuery("from "+clazz.getName()+" "+query).uniqueResult();
	}    
}
