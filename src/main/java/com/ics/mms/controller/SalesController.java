package com.ics.mms.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.ICashierService;
import com.ics.mms.service.IChartService;
import com.ics.mms.service.IItemService;
import com.ics.mms.service.ISalesService;
import com.ics.mms.service.IStockService;
import com.mms.pojo.Cashier;
import com.mms.pojo.Item;
import com.mms.pojo.Sales;
import com.mms.pojo.SoldItems;
import com.mms.pojo.Stock;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class SalesController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	ISalesService salesService;
	@Autowired
	ICashierService cashierService;
	@Autowired
	IItemService itemService;
	@Autowired
	IStockService stockService;
	@Autowired
	IChartService chartService;
	
	@RequestMapping(value = "salesForm", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		
		List<Cashier> cashierList = cashierService.listCashier();
		modelAndView.addObject("cashierList", cashierList);
		
		modelAndView.setViewName("sales/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "createSales", method=RequestMethod.POST)
	public ModelAndView createSales(@ModelAttribute @Valid Sales sales, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			Iterator<FieldError> iterator = bindingResult.getFieldErrors().iterator();
			while(iterator.hasNext()){
				FieldError fieldError =  iterator.next();
				System.out.println("Object: "+fieldError.getObjectName()+" Field : "+fieldError.getField()+" Error Msg : "+fieldError.getDefaultMessage()+" Value : "+fieldError.getRejectedValue());
			}
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.setViewName("sales/index");
			
		}else{
			String[] parts = request.getParameter("cashierId").split("_");
			String cashierId = parts[0];
			String cashierName = parts[1];
			
			Cashier cashier = new Cashier();
			cashier.setCashierId(Integer.parseInt(cashierId));
			sales.setCashier(cashier);
			Result result = salesService.addSales(sales);
			if(result.getResult() > 0){
				logger.info("Sales Id: "+result.getResult());
				sales.setSalesId(result.getResult());
				modelAndView.addObject(sales);
				List<Item> itemList = itemService.listItem();
				modelAndView.addObject(itemList);
				System.out.println("Cashier Name : "+cashierName);
				
				
				modelAndView.addObject("cashierName", cashierName);
				modelAndView.setViewName("sales/soldItems");
				return modelAndView;
			}else{
				
			}
			return index();
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value = "addSoldItem", method=RequestMethod.GET)
	public ModelAndView addSoldItem(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer salesId = Integer.parseInt(request.getParameter("salesId"));
		logger.info("Saled Id: "+salesId);
		Sales sales = salesService.getSales(salesId);
		modelAndView.addObject(sales);
		List<Item> itemList = itemService.listItem();
		modelAndView.addObject(itemList);
		modelAndView.addObject("cashierName", sales.getCashier().getCashierName());
		modelAndView.setViewName("sales/soldItems");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "createSoldItems", method=RequestMethod.POST)
	public @ResponseBody Integer createSoldItems(@ModelAttribute @Valid SoldItems soldItems, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer validate = 0;
		Integer salesId = Integer.parseInt(request.getParameter("salesId"));
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		String batchNo = request.getParameter("batchNo");
		String type = request.getParameter("type");
		String mfd = request.getParameter("manufacturedDate");
		String exp = request.getParameter("expiryDate");
		
		Item item = new Item();
		item.setItemId(itemId);
		soldItems.setItem(item);
		Sales sales = new Sales();
		sales.setSalesId(salesId);
		soldItems.setSales(sales);
		soldItems.setBatchNo(batchNo);
		soldItems.setManufacturedDate(mfd);
		soldItems.setExpiryDate(exp);
		
		Result result = salesService.addSoldItems(soldItems);
		if(result.getResult() > 0){
			logger.info("Sold Items Id: "+result.getResult());
			Stock stockData = stockService.getStock(item.getItemId(), batchNo);
			System.out.println("Stock: "+stockData);
			int qty = stockData.getQty()-soldItems.getQty();
			
			stockData.setQty(qty);
			
			Result stockUpdate = stockService.updateStock(stockData);
			logger.info("Stock Update: "+stockUpdate.getResult());
			validate = 1;	
			if(type.equals("finish")){
			validate = 2;
				soldItemsList(request, response, null);
			}
		}
		return validate;
	}//end of post

	@RequestMapping(value = "salesList", method=RequestMethod.GET)
	public ModelAndView salesList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("sales/list");
		
		List<Sales> salesList = salesService.listSales();
		Collections.reverse(salesList);
		modelAndView.addObject(salesList);
		
		List<SoldItems> listSoldItems = salesService.listSoldItems();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < salesList.size(); i++){
			int salesId = salesList.get(i).getSalesId();
			int count = getCount(listSoldItems, salesId);
			map.put(salesId, count);
		}
		modelAndView.addObject("map", map);
		modelAndView.addObject(listSoldItems);
		
		
		return modelAndView;
	}
	public Integer getCount(List<SoldItems> listSoldItems, int salesId){
		Integer count = 0;
		for(int i = 0; i < listSoldItems.size(); i++){
			if(listSoldItems.get(i).getSales().getSalesId() == salesId){
				System.out.println("Matched Id: "+salesId);
				count++;
			}
		}
		return count;
	}
	@RequestMapping(value = "soldItemsList", method=RequestMethod.GET)
	public ModelAndView soldItemsList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		Integer salesId = Integer.parseInt(request.getParameter("salesId"));
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("sales/soldItemsList");
		
		List<SoldItems> soldItemsList = salesService.listSoldItems(salesId, "salesId");
		Sales sales = salesService.getSales(salesId);
		modelAndView.addObject(sales);
		modelAndView.addObject(soldItemsList);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "getSales", method=RequestMethod.GET)
	public ModelAndView getSales(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("salesId"));
		modelAndView.setViewName("sales/edit");
		
		List<Cashier> cashierList = cashierService.listCashier();
		modelAndView.addObject("cashierList", cashierList);
		
		Sales sales = salesService.getSales(id);
		System.out.println("Sales Date: "+sales.getDate());
		modelAndView.addObject(sales);
		return modelAndView;
	}
	
	@RequestMapping(value = "getSoldItem", method=RequestMethod.GET)
	public ModelAndView getSoldItem(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("soldItemsId"));
		modelAndView.setViewName("sales/soldItemEdit");
		
		List<Item> itemList = itemService.listItem();
		modelAndView.addObject(itemList);
		
		SoldItems soldItem = salesService.getSoldItem(id);
		modelAndView.addObject("soldItem", soldItem);
		return modelAndView;
	}
	
	@RequestMapping(value = "updateSales", method=RequestMethod.POST)
	public ModelAndView updateSales(@ModelAttribute @Valid Sales sales, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sales/edit");
		
		logger.debug("Update Sales Started");

		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
		}else{
			String[] parts = request.getParameter("cashierId").split("_");
			String cashierId = parts[0];
			String cashierName = parts[1];
			
			Cashier cashier = new Cashier();
			cashier.setCashierId(Integer.parseInt(cashierId));
			sales.setCashier(cashier);
			
			Result result = salesService.updateSales(sales);
			if(result.getResult() == 1){
				model.put("message", "Sales Updated Successfully.");
				return salesList(request, response, model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value = "updateSoldItem", method=RequestMethod.POST)
	public ModelAndView updatePurchasedItem(@ModelAttribute @Valid SoldItems soldItem, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sales/soldItemEdit");
		
		logger.debug("Update Sold Item Started");

		if(bindingResult.hasErrors()){
			System.out.println("If............");
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(soldItem);
		}else{
			System.out.println("Else............"+request.getParameter("salesId")+" Item: "+request.getParameter("itemId"));
			Integer salesId = Integer.parseInt(request.getParameter("salesId"));
			Integer itemId = Integer.parseInt(request.getParameter("itemId"));
			System.out.println("Sales: "+salesId);
			System.out.println("Item: "+itemId);
			System.out.println("Sold Item: "+soldItem.getSoldItemsId());
			Sales sales = new Sales();
			sales.setSalesId(salesId);
			Item item = new Item();
			item.setItemId(itemId);
			soldItem.setSales(sales);
			soldItem.setItem(item);
			
			Result result = salesService.updateSoldItem(soldItem);
			if(result.getResult() == 1){
				model.put("message", "Sold Item Updated Successfully.");
				return soldItemsList(request, response, model);
			}
		}
		return modelAndView;
	}//end of post
	@RequestMapping(value="deleteSales", method=RequestMethod.GET)
	public ModelAndView deleteSales(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = Integer.parseInt(request.getParameter("salesId"));
		Result result = salesService.removeSales(id);
		if(result.getResult() == 1){
			model.put("message", "Sales Deleted Successfully.");
		}else{
			model.put("message", "Error Deleting Sales.");
		}
		return salesList(request, response, model);
	}
	
	@RequestMapping(value="deleteSoldItem", method=RequestMethod.GET)
	public ModelAndView deleteSoldItem(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Sold Item Id: "+request.getParameter("soldItemsId"));
		int id = Integer.parseInt(request.getParameter("soldItemsId"));
		Result result = salesService.removeSoldItem(id);
		if(result.getResult() == 1){
			model.put("message", "Sold Item Deleted Successfully.");
		}else{
			model.put("message", "Error Deleting Sold Item.");
		}
		return soldItemsList(request, response, model);
	}
	
	@RequestMapping(value="getHighchart", method=RequestMethod.POST)
	public ModelAndView getHighchart(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String chartName = request.getParameter("chart");
		
		ModelAndView view = new ModelAndView();
		
		List<Stock> stockList = stockService.listStock();
		Map<String, Object> dataRow = null;
		List<Object> dataList = new ArrayList<Object>();
		List<Object> nameDataList = new ArrayList<Object>();
		
		List<String> expiryDate = new ArrayList<String>();
		for(Stock stock : stockList){
			///expiryDate.add(stock.getExpiryDate());
			dataRow = new HashMap<String, Object>();
			dataRow.put("name", stock.getItem().getItemName()+" - "+stock.getBatchNo());
			dataRow.put("y", stock.getQty());
			dataList.add(dataRow);
		}
		
		List<String> categoriesList = new ArrayList<String>(Arrays.asList(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}));
		categoriesList = expiryDate;
		
		String categories = new Gson().toJson(categoriesList);
		
		//map1.put("name", "Tokyo");
		//map1.put("data", new Double[]{-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5});
		//map2.put("name", "Kathmandu");
		//map2.put("data", new Double[]{-1.2, 1.8, 5.7, 1.3, 7.0, 2.0, 4.8, 24.1, 20.1, 14.1, 2.6, 2.5});
		List<Object> list = new ArrayList<Object>();
		//list.add(map1);
		//list.add(map2);
		String jsonData = new Gson().toJson(dataList);
		jsonData = "data: "+jsonData;
		System.out.println("Json: "+jsonData);
		//jsonData = "[{ name: 'Browser share',data: [{name:'Firefox',y:45.0},{name:'IE',y:26.8},{name: 'Chrome',y: 12.8}]}]";
		System.out.println("Json: "+jsonData);
		String chartScript = chartService.getPieChart(chartName, "Top 10 Stocks", "Quantity", categories, jsonData);
		view.addObject("chartScript", chartScript);
		view.addObject("chartName", chartName);
		view.setViewName("chartTemplate"); //goes to jsp file because not in vm. Using jsp coz vm renders whole template
		return view;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
