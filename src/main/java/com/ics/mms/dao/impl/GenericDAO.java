package com.ics.mms.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.ics.mms.dao.IGenericDAO;


@Repository
public class GenericDAO< T extends Serializable > extends AbstractDAO<T> implements IGenericDAO<T>{
	//This is the Generic DAO class
	//All the rules are set in interface >> IGenericDAO
	//All the implementations are done in abstract >> AbstractDAO
	
	//So, we just don't write anything here...
}
