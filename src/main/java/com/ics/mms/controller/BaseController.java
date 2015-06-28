package com.ics.mms.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ics.mms.constant.AppConstant;
import com.ics.mms.constant.ErrorCodeConstant;
import com.ics.mms.constant.WebServiceConstant;
import com.ics.mms.context.AppContext;
import com.ics.mms.service.ISecurityService;




@Controller
public abstract class BaseController implements org.springframework.web.servlet.mvc.Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@Autowired
	protected AppContext appContext;
	@Autowired
	protected AppConstant appConstant;
	@Autowired
	protected WebServiceConstant webServiceConstant;
	@Autowired
	protected ErrorCodeConstant errorCodeConstant;
	@Autowired
	protected ISecurityService securityService;
	
	protected void setCoreJspParam(HashMap data){
		data.put("context_root", appContext.getContextRoot());
		data.put("spring_root", appContext.getSpringRoot());
	}
	

	protected Integer getUserId(){
		return securityService.getUserId();
	}
	
	protected int getBrowserType(HttpServletRequest request){
		logger.info("getBrowserType() begin");
		String useragent = request.getHeader("User-Agent");
		String user = useragent.toLowerCase();
		
		int result = -1;
		
		logger.debug(user);
		if(user.indexOf("msie") != -1) {
			result = 0;
		} else if(user.indexOf("chrome") != -1) {
			result = 1;
		} else if(user.indexOf("mozilla") != -1) {
			result = 2;
		}
		
		logger.debug(result);
		logger.info("getBrowserType() end");
		return result;
	}
}
