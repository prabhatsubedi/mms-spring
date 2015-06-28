package com.ics.mms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController extends BaseController implements org.springframework.web.servlet.mvc.Controller{

	@Override
	@RequestMapping("/404")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("errors/404");
		modelAndView.addObject("message", "The Requested URL could not be found.");
		return null;
	}

	@RequestMapping("/500")
	public ModelAndView error500(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		logger.error("Error: "+throwable.getStackTrace());
		ModelAndView modelAndView = new ModelAndView("errors/500");
		modelAndView.addObject("message", "The Server encounter issue. Please Contact Administrator.");
		modelAndView.addObject("error", throwable.getStackTrace());
		return modelAndView;
	}
}
