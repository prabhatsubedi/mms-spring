package com.ics.mms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ics.mms.context.CashierForm;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.ICashierService;
import com.mms.pojo.Cashier;


@Controller
public class CashierController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	ICashierService cashierService;
	
	@RequestMapping(value = "cashierForm", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		CashierForm cashierForm = new CashierForm();
		modelAndView.addObject(cashierForm);
		modelAndView.setViewName("cashier/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "createCashier", method=RequestMethod.POST)
	public ModelAndView createCashier(@Valid CashierForm cashierForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cashier/index");
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
		}else{
			String cashierName = cashierForm.getCashierName();
			
			logger.debug(cashierName);

			Cashier cashier = new Cashier();
			cashier.setCashierName(cashierName);
			Result result = cashierService.addCashier(cashier);
			if(result.getResult() == 1){
				model.put("message", "Cashier Created Successfully.");
				return cashierList(request, response, model);
			}else{
				model.put("message", result.getMessage());
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post

	@RequestMapping(value = "cashierList", method=RequestMethod.GET)
	public ModelAndView cashierList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("cashier/list");
		
		List<Cashier> cashierList = cashierService.listCashier();
		modelAndView.addObject(cashierList);
		return modelAndView;
	}
	
	@RequestMapping(value = "getCashier", method=RequestMethod.GET)
	public ModelAndView getCashier(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("cashierId"));
		modelAndView.setViewName("cashier/edit");
		Cashier cashier = cashierService.getCashier(id);
		System.out.println("Cashier name: "+cashier.getCashierName());
		CashierForm cashierForm = new CashierForm();
		modelAndView.addObject(cashierForm);
		modelAndView.addObject(cashier);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updateCashier", method=RequestMethod.POST)
	public ModelAndView updateCashier(@Valid CashierForm cashierForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cashier/edit");
		String cashierName = cashierForm.getCashierName();
		
		logger.debug(cashierName);

		Cashier cashier = new Cashier();
		cashier.setCashierId(cashierForm.getCashierId());
		cashier.setCashierName(cashierName);
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(cashier);
		}else{
			Result result = cashierService.updateCashier(cashier);
			if(result.getResult() == 1){
				model.put("message", "Cashier Updated Successfully.");
				return cashierList(request, response, model);
			}else{
				model.put("message", "Error Updating Cashier.");
				model.put("cashier", cashier);
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value="deleteCashier", method=RequestMethod.GET)
	public ModelAndView deleteCashier(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("cashierId"));
		Result result = cashierService.removeCashier(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cashier/list");
		if(result.getResult() == 1){
			model.put("message", "Cashier Deleted Successfully.");
			modelAndView.addObject(model);
		}else{
			model.put("message", "Error Deleting Cashier.");
			modelAndView.addObject(model);
		}
		List<Cashier> cashierList = cashierService.listCashier();
		modelAndView.addObject(cashierList);
		return modelAndView;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
