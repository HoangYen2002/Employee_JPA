package com.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		//nếu ko đăng nhập thì principal = null
		//String userName = principal.getName(); //<c:if test="${pageContext.request.userPrincipal.name != null}">
	   // System.out.println(userName);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/emp/get/{id}", method = RequestMethod.GET)
	public String getEmployee(Locale locale, Model model, @PathVariable("id") int id, Principal principal) {
	   // nếu có đăng nhập thì sẽ lấy đc username thông qua principal
     //  String userName = principal.getName(); //<c:if test="${pageContext.request.userPrincipal.name != null}">
       //System.out.println(userName);
		Date date = new Date();//java.util.Date
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);//java.text.DateFormat
		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("id", id);
		//model.addAttribute("name", userName);

		return "employee";
	}

	@RequestMapping(value = "/login123")
	public String login(HttpServletRequest request, Model model) {
		return "login123";
	}

	@RequestMapping(value = "/logout123")
	public String logout() {
		return "logout123";
	}

	@RequestMapping(value = "/denied")
	public String denied() {
		return "denied";
	}
}