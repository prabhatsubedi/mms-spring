package com.ics.mms.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mms.pojo.User;

@Repository
@Transactional
public abstract class AbstractJpaDao< T extends Serializable > {
	private Class< T > clazz;
	
	@Autowired
    protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 
	public void setClazz( Class< T > clazzToSet ){
		this.clazz = clazzToSet;
	}
 
	public User checkLogin(String loginname, String password){
		System.out.println("Login Name: "+loginname);
		User userr = new User();
		userr.setLastname("Prabhat");
		userr.setLoginname("login");
		sessionFactory.getCurrentSession().save(userr);
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u WHERE u.loginname = :login AND u.password = :pass");
        q.setParameter("login", loginname);
        q.setParameter("pass", password);
        User user = null;
        try{
        	 user = (User) q.getSingleResult(); 
        }catch(Exception ex){
        	System.out.println("No User Found");
        	System.out.println("Exception: "+ex.getMessage());
        }
        return user;
	   }
	
	public T findOne( Integer id ){
		return null;
		//return entityManager.find( clazz, id );
	}
	@SuppressWarnings("unchecked")
	public List< T > findAll(){
		return null;
		//return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
	}
 
	@SuppressWarnings("unchecked")
	public List< T > findAllById(Object columnValue, String columnName){
		return null;
		//return entityManager.createQuery( "from " + clazz.getName() +" where "+columnName+" = "+columnValue).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Object columnValue, String columnName){
		System.out.println("Column Name: "+columnName+" Column Value: "+columnValue);
		return null;
		//return (T) entityManager.createQuery( "from " + clazz.getName() +" where "+columnName+" = "+"'"+columnValue+"'").getSingleResult();
	}
	
	public void create( T entity ){
		//entityManager.persist( entity );
	}
 
	public void update( T entity ){
		//entityManager.merge( entity );
	}
 
	public void delete( T entity ){
		//entityManager.remove( entity );
	}
	public void deleteById( Integer entityId ){
		T entity = findOne( entityId );
		delete( entity );
	}	
	
	@SuppressWarnings("unchecked")
	public T findByQuery(String query){
		T entity = null;
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from "+clazz.getName()+" "+query);
		try{
			entity = (T) q.getSingleResult(); 
		}catch(Exception e){
			System.out.println("EJB Exception: "+e);
		}
        	 return entity;
	   }
}
