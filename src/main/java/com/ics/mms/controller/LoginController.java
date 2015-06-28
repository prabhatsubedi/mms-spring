package com.ics.mms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ics.mms.context.LoginForm;
import com.ics.mms.http.response.json.LoginDataObject;
import com.ics.mms.service.IClientService;
import com.ics.mms.service.ISecurityService;
import com.ics.mms.service.IWebServiceClientService;
import com.mms.pojo.Client;


@Controller
public class LoginController extends BaseController implements
		org.springframework.web.servlet.mvc.Controller {

	@Autowired
	IWebServiceClientService webServiceClientService;
	@Autowired
	ISecurityService securityService;
	@Autowired
	IClientService clientService;

	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("login() begin");
		System.out.println("Domain: " + request.getHeader("Host"));
		String domain = request.getRequestURL()
				.substring(request.getRequestURL().indexOf("//") + 2)
				.split(":")[0];
		String customer = domain.substring(0, domain.indexOf("."));

		// http://localhost:8080/mms/login
		System.out.println("Customer : " + customer);
		System.out.println("Domain: " + request.getHeader("Host"));

		Client client = clientService.getClient(customer);
		if (client != null) {
			System.out.println("Clint ID: " + client.getClientId()
					+ " D Name: " + client.getDisplayName());
		}

		HashMap<String, String> data = new HashMap<String, String>();

		data.put("string1", appContext.getTestStr());

		logger.debug("debug log here");
		HttpSession session = request.getSession(false);
		System.out.println("Session : " + session);
		System.out
				.println("Session User Id: " + session.getAttribute("userID"));
		logger.info("login() end");
		if (session.getAttribute("userID") != null) {
			return dashboard(request, response);
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "loginform.html", method = RequestMethod.POST)
	public ModelAndView processForm(@Valid LoginForm loginForm,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Login Process Begin..........");
		String userName = loginForm.getUserName();
		String password = loginForm.getPassword();

		logger.debug(userName);
		// logger.debug(password);

		HttpClient httpclient = new DefaultHttpClient();

		LoginDataObject list = securityService.login(httpclient, request,
				userName, password);

		if (list == null) {
			/*
			 * TODO ray: please return with error message
			 */
			return new ModelAndView("login");
		}

		logger.debug(list.getresult());
		logger.debug(list.getuid());
		logger.debug(list.getopenFireID());

		int userId = list.getuid();

		if (list.getresult() == 1) {
			logger.info("login is ok");

			logger.debug("saving uid to session");

			HttpSession session = request.getSession(true);
			session.setAttribute("userID", userId);

			logger.debug("session userId = " + userId);
			return dashboard(request, response);
		} else {
			logger.debug("login failed");
		}
		return new ModelAndView("login");
	}// end of post

	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public ModelAndView dashboard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("dashboard/index");
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("logout begin");
		securityService.logout(request);
		logger.debug("logout success");
		return new ModelAndView("login");
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
