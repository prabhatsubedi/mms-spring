package com.ics.mms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ics.mms.service.IStockService;
import com.mms.pojo.Stock;


@Controller
public class StockController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	IStockService stockService;
	

	@RequestMapping(value = "stockList", method=RequestMethod.GET)
	public ModelAndView partyList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		List<String> columnList = Arrays.asList(new String[] {"S.No.", "Stock ID", "Item ID", "Item Name", "Battch No.", "Quantity", "Rate", "Manufactured Date", "Expiry Date"});
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("stock/list");
		
		List<Stock> stockList = stockService.listStock();
		modelAndView.addObject("stockList", stockList);
		modelAndView.addObject("columnList", columnList);
		return modelAndView;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
