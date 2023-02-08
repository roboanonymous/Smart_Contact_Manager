package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User1;

@Controller
public class HomeController {

	// Home Handler
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title", "Home - smart contact Manager");
		return "home";
	}
	
	// About Handler
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title", "About - smart contact Manager");
		return "about";
	}
	
	// Signup Handler
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title", "Register - smart contact Manager");
		return "signup";
	}
}
