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

import com.ics.mms.context.PartyForm;
import com.ics.mms.http.response.json.Result;
import com.ics.mms.service.IPartyService;
import com.ics.mms.service.IWebServiceClientService;
import com.mms.pojo.Party;


@Controller
public class PartyController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Autowired
	IPartyService partyService;
	
	@RequestMapping(value = "partyForm", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		PartyForm partyForm = new PartyForm();
		modelAndView.addObject(partyForm);
		modelAndView.setViewName("party/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "createParty", method=RequestMethod.POST)
	public ModelAndView createParty(@Valid PartyForm partyForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("party/index");
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
		}else{
			String partyName = partyForm.getPartyName();
			
			logger.debug(partyName);

			Party party = new Party();
			party.setPartyName(partyName);
			party.setType(partyForm.getType());
			Result result = partyService.addParty(party);
			if(result.getResult() == 1){
				model.put("message", "Party Created Successfully.");
				return partyList(request, response, model);
			}else{
				model.put("message", result.getMessage());
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post

	@RequestMapping(value = "partyList", method=RequestMethod.GET)
	public ModelAndView partyList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("party/list");
		
		List<Party> partyList = partyService.listParty();
		modelAndView.addObject(partyList);
		return modelAndView;
	}
	
	@RequestMapping(value = "viewParty", method=RequestMethod.GET)
	public ModelAndView viewParty(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("partyId"));
		modelAndView.setViewName("party/view");
		Party party = partyService.getParty(id);
		System.out.println("Party name: "+party.getPartyName());
		modelAndView.addObject(party);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "getParty", method=RequestMethod.GET)
	public ModelAndView getParty(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		Integer id = Integer.parseInt(request.getParameter("partyId"));
		modelAndView.setViewName("party/edit");
		Party party = partyService.getParty(id);
		System.out.println("Party name: "+party.getPartyName());
		PartyForm partyForm = new PartyForm();
		modelAndView.addObject(partyForm);
		modelAndView.addObject(party);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updateParty", method=RequestMethod.POST)
	public ModelAndView updateParty(@Valid PartyForm partyForm, BindingResult bindingResult,  Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("party/edit");
		String partyName = partyForm.getPartyName();
		
		logger.debug(partyName);

		Party party = new Party();
		party.setPartyId(partyForm.getPartyId());
		party.setPartyName(partyName);
		party.setType(partyForm.getType());
		if(bindingResult.hasErrors()){
			model.put("error", "This is server side error. Please validate in front-end");
			modelAndView.addObject(model);
			modelAndView.addObject(party);
		}else{
			Result result = partyService.updateParty(party);
			if(result.getResult() == 1){
				model.put("message", "Party Updated Successfully.");
				return partyList(request, response, model);
			}else{
				model.put("message", "Error Updaing party.");
				model.put("party", party);
				modelAndView.addObject(model);
			}
		}
		return modelAndView;
	}//end of post
	
	@RequestMapping(value="deleteParty", method=RequestMethod.GET)
	public ModelAndView deleteParty(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("partyId"));
		Result result = partyService.removeParty(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("party/list");
		if(result.getResult() == 1){
			model.put("message", "Party Deleted Successfully.");
			modelAndView.addObject(model);
		}else{
			model.put("message", "Error Deleting Party.");
			modelAndView.addObject(model);
		}
		List<Party> partyList = partyService.listParty();
		modelAndView.addObject(partyList);
		return modelAndView;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
