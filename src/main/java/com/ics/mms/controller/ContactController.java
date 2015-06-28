package com.ics.mms.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.ics.mms.http.request.json.AddContactReqJson;
import com.ics.mms.http.request.json.DeleteContactReqJson;
import com.ics.mms.http.response.json.GetContactResJson;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IContactService;
import com.ics.mms.service.IWebServiceClientService;


@Controller
public class ContactController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	IWebServiceClientService webServiceClientService;
	@Autowired
	IContactService contactService;
	
	@Override
	@RequestMapping("/contact")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		logger.info("contact() begin");
		
		List<GetContactResJson> list = null;
		
		logger.info("contact() end");
		
		ModelAndView modelAndView = new ModelAndView("contact");
		modelAndView.addObject("contactList", list);
		
		return modelAndView;
	}

	/**
	 * @param json
	 * @return Result
	 */
	@RequestMapping(value="/proxy/contact/add", method=RequestMethod.POST)
	@ResponseBody
	public Result proxyContactAdd(@RequestParam(value = "json", required = false) String json){
		logger.info("proxyContactAdd() begin");
		try {
			AddContactReqJson contactJson = new Gson().fromJson(json, AddContactReqJson.class);
			Result result = contactService.addContact(contactJson.getFirstName(), contactJson.getLastName(), contactJson.getTelephone(), contactJson.getEmail());
			logger.info("proxyContactAdd() end");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("proxyContactAdd() end");
			return new Result(0,"failed");
		}
	}
	
	/**
	 * @return List of Contacts
	 */
	@RequestMapping(value="/proxy/contact/get", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView listContact(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		
		logger.info("contact proxy get() begin");
		
		List<GetContactResJson> list = contactService.listContact();		
		
		logger.info("contact proxy get() end");
		
		ModelAndView modelAndView = new ModelAndView("contact_list_ajax");
		modelAndView.addObject("contactList", list);
		
		return modelAndView;
	}
	
	/**
	 * @param json
	 * @return Result
	 */
	@RequestMapping(value="/proxy/contact/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result proxyContactDelete(@RequestParam(value = "json", required = false) String json){
		logger.info("proxyContactDelete() begin");
		try {
			DeleteContactReqJson contactJson = new Gson().fromJson(json, DeleteContactReqJson.class);
			Result result = contactService.removeContact(contactJson.getId());
			logger.info("proxyContactDelete() end");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("proxyContactDelete() end");
			return new Result(0,"failed");
		}
	}
}
