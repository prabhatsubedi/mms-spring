package com.ics.mms.service.impl;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ics.mms.constant.AppConstant;
import com.ics.mms.constant.WebServiceConstant;
import com.ics.mms.context.AppContext;
import com.ics.mms.service.IBaseService;
import com.mms.pojo.Item;



public class BaseService implements IBaseService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected AppContext appContext;
	@Autowired
	protected AppConstant appConstant;
	@Autowired
	protected WebServiceConstant webServiceConstant;

	InitialContext ic;
}
