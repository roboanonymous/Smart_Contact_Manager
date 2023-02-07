package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User1;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	@ResponseBody
	public String test()
	{
		User1 user1 = new User1();
		user1.setName("Samyak Jain");
		user1.setEmail("samyakjain@gmail.com");
		userRepository.save(user1);
		return "working";
	}
}
