package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User1;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	public UserRepository UserRepository;
	
	
	// method of adding common data to response
	@ModelAttribute
	public void commonUserData(Model model, Principal principal) {
	
          String username = principal.getName();
		  
		  User1 user1 = UserRepository.getUserbyUsername(username);
		  
		  model.addAttribute("user1", user1);
	}
	
	
	/* Dashboard home handler*/
	@RequestMapping("/index")
	public String dashboard(Model model , Principal principal)
	{
		
		  String username = principal.getName();
		  
		  User1 user1 = UserRepository.getUserbyUsername(username);
		  
		  model.addAttribute("user1", user1);
		 
		
		return "normal/user_dashboard";
	}
	
	
	/* Handler to add contact to user dash-board*/
	@GetMapping("/add-contact-form")
	public String addUserContact(Model model) {
			
		model.addAttribute("title", "Add contact : Smart contact Manager");
		model.addAttribute("contact",new Contact());	
		return "normal/add_contact_form";
	
	}

}
