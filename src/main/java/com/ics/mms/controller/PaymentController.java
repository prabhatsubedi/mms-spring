package com.ics.mms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController extends BaseController implements org.springframework.web.servlet.mvc.Controller{
	
	@RequestMapping(value = "paymentList", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request){
		ModelAndView paymentList = new ModelAndView();
		
		paymentList.setViewName("payment/list");
		return paymentList;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
