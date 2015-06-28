package com.ics.mms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ics.mms.context.PurchaseForm;
import com.ics.mms.context.PurchasedItemsForm;
import com.ics.mms.http.response.json.PurchasedItemsJson;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IItemService;
import com.ics.mms.service.IPartyService;
import com.ics.mms.service.IPurchaseService;
import com.ics.mms.service.IStockService;
import com.mms.pojo.Item;
import com.mms.pojo.Party;
import com.mms.pojo.Purchase;
import com.mms.pojo.PurchasedItems;
import com.mms.pojo.Stock;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class PurchaseController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	IPurchaseService purchaseService;
	@Autowired
	IPartyService partyService;
	@Autowired
	IItemService itemService;
	@Autowired
	IStockService stockService;
	
	@RequestMapping(value = "purchaseForm", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		
		List<Party> partyList = partyService.listParty();
		List<Party> supplierList = new ArrayList<Party>();
		for(Party party : partyList){
			if(party.getType().equals("Supplier")){
				supplierList.add(party);
			}
		}
		modelAndView.addObject("supplierList", supplierList);
		
		PurchaseForm purchaseForm = new PurchaseForm();
		modelAndView.addObject(purchaseForm);
		modelAndView.setViewName("purchase/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "createPurchase", method=RequestMethod.POST)
	public ModelAndView createPurchase(@Valid PurchaseForm purchaseForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.setViewName("purchase/index");
			modelAndView.addObject(model);
		}else{
			String date = purchaseForm.getDate();
			
			logger.debug(date);

			Purchase purchase = new Purchase();
			purchase.setDate(date);
			purchase.setPurchaseType(purchaseForm.getPurchaseType());
			purchase.setInvoiceNo(purchaseForm.getInvoiceNo());
			
			String[] parts = purchaseForm.getPartyId().split("_");
			String partyId = parts[0];
			String partyName = parts[1];
			Party party = new Party();
			party.setPartyId(Integer.parseInt(partyId));
			purchase.setParty(party);
			Result result = purchaseService.addPurchase(purchase);
			if(result.getResult() > 0){
				logger.info("Purched Id: "+result.getResult());
				purchaseForm.setId(result.getResult());
				purchaseForm.setPartyName(partyName);
				modelAndView.addObject(purchaseForm);
				PurchasedItemsForm purchasedItemsForm = new PurchasedItemsForm();
				modelAndView.addObject(purchasedItemsForm);
				List<Item> itemList = itemService.listItem();
				modelAndView.addObject(itemList);
				modelAndView.setViewName("purchase/purchasedItems");
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value = "addPurchasedItem", method=RequestMethod.GET)
	public ModelAndView addPurchasedItem(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		PurchaseForm purchaseForm = new PurchaseForm();
		Integer purchaseId = Integer.parseInt(request.getParameter("purchaseId"));
		logger.info("Purched Id: "+purchaseId);
		Purchase purchase = purchaseService.getPurchase(purchaseId);
		purchaseForm.setId(purchaseId);
		purchaseForm.setPartyName(purchase.getParty().getPartyName());
		purchaseForm.setInvoiceNo(purchase.getInvoiceNo());
		purchaseForm.setDate(purchase.getDate());
		modelAndView.addObject(purchaseForm);
		PurchasedItemsForm purchasedItemsForm = new PurchasedItemsForm();
		modelAndView.addObject(purchasedItemsForm);
		List<Item> itemList = itemService.listItem();
		modelAndView.addObject(itemList);
		modelAndView.setViewName("purchase/purchasedItems");
		
		return modelAndView;
	}
	@RequestMapping(value = "createPurchasedItems", method=RequestMethod.POST)
	public @ResponseBody Integer createPurchasedItems(@ModelAttribute @Valid PurchasedItems purchasedItems, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer validate = 0;
		Integer purchaseId = Integer.parseInt(request.getParameter("purchaseId"));
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		String type = request.getParameter("type");
		System.out.println("Type: "+type);
		
		Item item = new Item();
		item.setItemId(itemId);
		purchasedItems.setItem(item);
		Purchase purchase = new Purchase();
		purchase.setPurchaseId(purchaseId);
		purchasedItems.setPurchase(purchase);
		
		Result result = purchaseService.addPurchasedItems(purchasedItems);
		if(result.getResult() > 0){
			logger.info("Purchased Items Id: "+result.getResult());
			Stock stock = new Stock();
			stock.setBatchNo(purchasedItems.getBatchNo());
			stock.setItem(item);
			stock.setQty(purchasedItems.getQty());
			stock.setRate(purchasedItems.getRate());
			stock.setExpiryDate(purchasedItems.getExpiryDate());
			stock.setManufacturedDate(purchasedItems.getManufacturedDate());
			
			Stock stockData = stockService.getStock(item.getItemId(), purchasedItems.getBatchNo());
			System.out.println("Stock: "+stockData);
			if(stockData != null){
				int qty = stockData.getQty()+purchasedItems.getQty();
				stock.setQty(qty);
				stock.setStockId(stockData.getStockId());
				stockService.updateStock(stock);
				logger.info("Stock If.....");
			}else{
				logger.info("Stock Else.....");
				stockService.addStock(stock);
			}
			validate = 1;	
			if(type.equals("finish")){
			validate = 2;
				listPurchasedItems(request, response, null);
			}
		}
		return validate;
	}//end of post

	@RequestMapping(value = "purchaseList", method=RequestMethod.GET)
	public ModelAndView purchaseList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("purchase/list");
		
		List<Purchase> purchaseList = purchaseService.listPurchase();
		Collections.reverse(purchaseList);
		modelAndView.addObject(purchaseList);
		
		List<PurchasedItems> listPurchasedItems = purchaseService.listPurchasedItems();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < purchaseList.size(); i++){
			int purchaseId = purchaseList.get(i).getPurchaseId();
			int count = getCount(listPurchasedItems, purchaseId);
			map.put(purchaseId, count);
		}
		modelAndView.addObject(listPurchasedItems);
		modelAndView.addObject("map", map);
		return modelAndView;
	}
	@RequestMapping(value = "purchasedItemsList", method=RequestMethod.GET)
	public ModelAndView listPurchasedItems(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		logger.info("Purchased Items List Started..........");
		Integer purchaseId = Integer.parseInt(request.getParameter("purchaseId"));
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("purchase/purchasedItemsList");
		
		List<PurchasedItems> purchasedItemsList = purchaseService.listPurchasedItems(purchaseId, "purchaseId");
		Purchase purchase = purchaseService.getPurchase(purchaseId);
		modelAndView.addObject(purchase);
		modelAndView.addObject(purchasedItemsList);
		
		return modelAndView;
	}
	public Integer getCount(List<PurchasedItems> listPurchasedItems, int purchaseId){
		Integer count = 0;
		for(int i = 0; i < listPurchasedItems.size(); i++){
			if(listPurchasedItems.get(i).getPurchase().getPurchaseId() == purchaseId){
				System.out.println("Matched Id: "+purchaseId);
				count++;
			}
		}
		return count;
	}
	@RequestMapping(value = "getPurchase", method=RequestMethod.GET)
	public ModelAndView getPurchase(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("purchaseId"));
		modelAndView.setViewName("purchase/edit");
		Purchase purchase = purchaseService.getPurchase(id);
		System.out.println("Purchase Date: "+purchase.getDate());
		List<Party> partyList = partyService.listParty();
		List<Party> supplierList = new ArrayList<Party>();
		for(Party party : partyList){
			if(party.getType().equals("Supplier")){
				supplierList.add(party);
			}
		}
		modelAndView.addObject("supplierList", supplierList);
		modelAndView.addObject(purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "getPurchasedItem", method=RequestMethod.GET)
	public ModelAndView getPurchasedItem(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("purchasedItemsId"));
		modelAndView.setViewName("purchase/purchasedItemEdit");
		PurchasedItems purchasedItem = purchaseService.getPurchasedItem(id);
		modelAndView.addObject("purchasedItem", purchasedItem);
		List<Item> itemList = itemService.listItem();
		modelAndView.addObject(itemList);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updatePurchase", method=RequestMethod.POST)
	public ModelAndView updatePurchase(@ModelAttribute @Valid Purchase purchase, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/edit");
		
		logger.debug("Update Purchase Started");

		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(purchase);
		}else{
			String[] parts = request.getParameter("partyId").split("_");
			String partyId = parts[0];
			String partyName = parts[1];
			Party party = new Party();
			party.setPartyId(Integer.parseInt(partyId));
			purchase.setParty(party);
			
			Result result = purchaseService.updatePurchase(purchase);
			if(result.getResult() == 1){
				model.put("message", "Purchase Updated Successfully.");
				return purchaseList(request, response, model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value = "updatePurchasedItem", method=RequestMethod.POST)
	public ModelAndView updatePurchasedItem(@ModelAttribute @Valid PurchasedItems purchasedItem, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/purchasedItemEdit");
		
		logger.debug("Update Purchased Items Started");

		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(purchasedItem);
		}else{
			Integer purchaseId = Integer.parseInt(request.getParameter("purchaseId"));
			Integer itemId = Integer.parseInt(request.getParameter("itemId"));
			
			Purchase purchase = new Purchase();
			purchase.setPurchaseId(purchaseId);
			Item item = new Item();
			item.setItemId(itemId);
			purchasedItem.setPurchase(purchase);
			purchasedItem.setItem(item);
			
			Result result = purchaseService.updatePurchasedItems(purchasedItem);
			if(result.getResult() == 1){
				model.put("message", "Purchased Item Updated Successfully.");
				return listPurchasedItems(request, response, model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value="deletePurchase", method=RequestMethod.GET)
	public ModelAndView deletePurchase(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = Integer.parseInt(request.getParameter("purchaseId"));
		Result result = purchaseService.removePurchase(id);
		if(result.getResult() == 1){
			model.put("message", "Purchase Deleted Successfully.");
		}else{
			model.put("message", "Error Deleting Purchase.");
		}
		return purchaseList(request, response, model);
	}
	
	@RequestMapping(value="deletePurchasedItem", method=RequestMethod.GET)
	public ModelAndView deletePurchasedItem(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = Integer.parseInt(request.getParameter("purchasedItemsId"));
		Result result = purchaseService.removePurchasedItem(id);
		if(result.getResult() == 1){
			model.put("message", "Purchased Item Deleted Successfully.");
		}else{
			model.put("message", "Error Deleting Purchased Item.");
		}
		return listPurchasedItems(request, response, model);
	}
	
	@RequestMapping(value = "purchasedItemsListByItem", method=RequestMethod.POST)
	@ResponseBody
	public List<PurchasedItemsJson> purchasedItemsListByItem(HttpServletRequest request) throws Exception {
		logger.info("Purchased Items List By Item Started..........");
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		System.out.println("Item Id: "+itemId);
		List<PurchasedItemsJson> purchasedItemsListJson = new ArrayList<PurchasedItemsJson>();
		List<PurchasedItems> purchasedItemsList = new ArrayList<PurchasedItems>();
		purchasedItemsList = purchaseService.listPurchasedItems(itemId, "itemId");
		for(PurchasedItems purchasedItem : purchasedItemsList){
			PurchasedItemsJson purchasedItemsJson = new PurchasedItemsJson();
			purchasedItemsJson.setPurchasedItemsId(purchasedItem.getPurchasedItemsId());
			purchasedItemsJson.setBatchNo(purchasedItem.getBatchNo());
			purchasedItemsJson.setManufacturedDate(purchasedItem.getManufacturedDate());
			purchasedItemsJson.setExpiryDate(purchasedItem.getExpiryDate());
			purchasedItemsJson.setQty(purchasedItem.getQty());
			purchasedItemsJson.setRate(purchasedItem.getRate());
			purchasedItemsJson.setAmount(purchasedItem.getAmount());
			
			Item item = new Item();
			item = purchasedItem.getItem();
			
			Purchase purchase = new Purchase();
			purchase.setDate(purchasedItem.getPurchase().getDate());
			
			purchasedItemsJson.setItem(item);
			purchasedItemsJson.setPurchase(purchase);
			purchasedItemsListJson.add(purchasedItemsJson);
		}
		System.out.println("LIst Size: "+purchasedItemsList.size());
		
		return purchasedItemsListJson;
	}
	
	@RequestMapping(value = "ajaxPurchasedItemDetailByItem", method=RequestMethod.POST)
	@ResponseBody
	public Stock ajaxPurchasedItemDetailByItem(HttpServletRequest request){
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		System.out.println("Item Id : "+itemId);
		List<Stock> stockItemList = stockService.listStockByItem(itemId);
		Stock itemStock = null;
		if(stockItemList.size() > 0){
			if(stockItemList.size() > 1){
				for(Stock stock : stockItemList){
					System.out.println("Stock ID: "+stock.getQty());
					if(stock.getQty() > 0){
						itemStock = stock;
						break;
					}
					
				}
			}else{
				
			}
		}
		
		
		return itemStock;
	}
	
	@RequestMapping(value = "ajaxPurchasedItemsListByItem", method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView ajaxPurchasedItemsListByItem(HttpServletRequest request){
		logger.info("Purchased Items List By Item Started..........");
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		System.out.println("Item Id: "+itemId);
		List<Stock> purchasedItemsList = stockService.listStockByItem(itemId);
		System.out.println("LIst Size: "+purchasedItemsList.size());
		
		ModelAndView view = new ModelAndView();
		view.addObject("purchasedItemsList", purchasedItemsList);
		view.setViewName("itemDetails"); //goes to jsp file because not in vm
		return view;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
