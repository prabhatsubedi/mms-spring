package com.ics.mms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootController {
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView dashboard(){
		return new ModelAndView("dashboard/index");	
	}
}
