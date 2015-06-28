package com.ics.mms.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ics.mms.context.ItemForm;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IItemService;
import com.mms.pojo.Item;


@Controller
public class ItemController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	IItemService itemService;
	
	@RequestMapping(value = "itemForm", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		ItemForm itemForm = new ItemForm();
		modelAndView.addObject(itemForm);
		modelAndView.setViewName("item/index");
		return modelAndView;
	}
	@RequestMapping(value = "modelTest", method=RequestMethod.GET)
	public ModelAndView modelTest(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("item/modelTest");
		return modelAndView;
	}
	@RequestMapping(value = "createModelTest", method=RequestMethod.POST)
	public ModelAndView createModelTest(@ModelAttribute @Valid Item item, BindingResult bindingResult, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			Iterator<FieldError> iterator = bindingResult.getFieldErrors().iterator();
			while(iterator.hasNext()){
				FieldError fieldError =  iterator.next();
				System.out.println("Object: "+fieldError.getObjectName()+" Field : "+fieldError.getField()+" Error Msg : "+fieldError.getDefaultMessage()+" Value : "+fieldError.getRejectedValue());
			}
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.setViewName("item/modelTest");
			
		}else{
			System.out.println("Item Name: "+item.getItemName());
			return index();
		}
		return modelAndView;
	}//end of post
	@RequestMapping(value = "createItem", method=RequestMethod.POST)
	public ModelAndView createItem(@Valid ItemForm itemForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("item/index");
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
		}else{
			String itemName = itemForm.getItemName();
			
			logger.debug(itemName);

			Item item = new Item();
			item.setItemName(itemName);
			item.setItemPrice(itemForm.getItemPrice());
			Result result = itemService.addItem(item);
			if(result.getResult() == 1){
				model.put("message", "Item Created Successfully.");
				return itemList(request, response, model);
			}else{
				model.put("message", result.getMessage());
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post

	@RequestMapping(value = "itemList", method=RequestMethod.GET)
	public ModelAndView itemList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("item/list");
		
		List<Item> itemList = itemService.listItem();
		System.out.println("Item List Size: "+itemList.size());
		for(int i =0; i<itemList.size(); i++){
			System.out.println("Item : "+itemList.get(i));
		}
		
		modelAndView.addObject("itemList", itemList);
		return modelAndView;
	}
	
	@RequestMapping(value = "getItem", method=RequestMethod.GET)
	public ModelAndView getItem(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("itemId"));
		modelAndView.setViewName("item/edit");
		Item item = itemService.getItem(id);
		System.out.println("Item name: "+item.getItemName());
		ItemForm itemForm = new ItemForm();
		modelAndView.addObject(itemForm);
		modelAndView.addObject(item);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updateItem", method=RequestMethod.POST)
	public ModelAndView updateItem(@Valid ItemForm itemForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("item/edit");
		String itemName = itemForm.getItemName();
		
		logger.debug(itemName);

		Item item = new Item();
		item.setItemId(itemForm.getItemId());
		item.setItemName(itemName);
		item.setItemPrice(itemForm.getItemPrice());
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(item);
		}else{
			Result result = itemService.updateItem(item);
			if(result.getResult() == 1){
				model.put("message", "Item Updated Successfully.");
				return itemList(request, response, model);
			}{
				model.put("message", "Error Updating Item.");
				model.put("item", item);
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value="deleteItem", method=RequestMethod.GET)
	public ModelAndView deleteItem(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("itemId"));
		Result result = itemService.removeItem(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("item/list");
		if(result.getResult() == 1){
			model.put("message", "Item Deleted Successfully.");
			modelAndView.addObject(model);
		}else{
			model.put("message", "Error Deleting Item.");
			modelAndView.addObject(model);
		}
		List<Item> itemList = itemService.listItem();
		modelAndView.addObject(itemList);
		return modelAndView;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
