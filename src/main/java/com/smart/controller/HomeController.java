package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User1;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;

	// Home Handler
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - smart contact Manager");
		return "home";
	}

	// About Handler
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - smart contact Manager");
		return "about";
	}

	// Signup Handler
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - smart contact Manager");
		model.addAttribute("user1", new User1());
		return "signup";
	}

	
	  //handler for registering user
	  
	 @RequestMapping(value ="/do_register", method=RequestMethod.POST)
	 public String registerUser(@Valid @ModelAttribute("user1") User1 user1, @RequestParam(value= "agreement" , defaultValue = "false") boolean agreement , Model model, 
			 BindingResult result1 ,HttpSession session) {
	  
		 try {
			 if(!agreement)
			 {
				 System.out.println("You have not check terms and conditions");
				 throw new Exception("You have not check terms and conditions");
			 }
			 
			 if(result1.hasErrors())
			 {
				 System.out.println("Error " + result1.toString());
				 model.addAttribute("user1" , user1);
				 return "signup";
			 }
			 
			 user1.setRole("Role_User");
			 user1.setEnabled(true);
			 user1.setImageURL("default.png");
			 user1.setPassword(passwordEncoder.encode(user1.getPassword()));
			 
			 User1 result = this.userRepository.save(user1);
			 
			 System.out.println("Agreement" + agreement); 
		     System.out.println("USER1" + user1); 
		     
		     model.addAttribute("user1" ,new User1());
		     session.setAttribute("message", new Message ("Successful Registration ", "alert-success"));
			 return "signup"; 
		 
			 
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			 model.addAttribute("user1", user1);
			 session.setAttribute("message", new Message ("Something went wrong !! " + e.getMessage(), "alert-error"));
			 return "signup"; 
		}
		 
		 
	 }
	 

}
