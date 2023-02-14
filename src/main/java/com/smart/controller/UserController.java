package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User1;
import com.smart.helper.Message;

import jakarta.persistence.criteria.Path;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	public UserRepository UserRepository;
	
	@Autowired
	public ContactRepository ContactRepository;
	
	
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
	
	/**
	 *Add contact-form-data processing to store in
	 *respective users account
	 **/
	@PostMapping("/process-contact")
	public String processAddContact(@Valid @ModelAttribute Contact contact, BindingResult result ,@RequestParam("profileImage") MultipartFile file,Principal principal, Model model, HttpSession session) {
		
		
		
		try {
			
			String name = principal.getName();
			User1 user1 = this.UserRepository.getUserbyUsername(name);
			
			//processing and Uploading File
			if(file.isEmpty())
			{
				// if the file is empty
				System.out.println("File is empty");
				contact.setImage("contact.png");
				
			}
			
			else
			{
				// file the file to folder
				contact.setImage(file.getOriginalFilename());
				File saveFile =new ClassPathResource("static/img").getFile();
				
				java.nio.file.Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Image is uploaded");
			}
			
			
			
			contact.setUser(user1);
			user1.getContacts().add(contact);
			
			this.UserRepository.save(user1);
			
			
			System.out.println("Data  " + contact);
			System.out.println("Added to Database");
			
			/** success message alert */
			session.setAttribute("message", new Message("Contact saved successfully.....!!", "success"));
			
			
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Something goes wrong, please try again.....!!", "danger"));
		}

		return "normal/add_contact_form";
	}
	
	// Show Contact handler
	@GetMapping("/show-contacts/{page}")
	public String showContacts( @PathVariable("page") Integer page, Model m , Principal principal)
	{
		m.addAttribute("title", "Show Contacts : Smart contact Manager");
		
		String username = principal.getName();
		User1 user1 = this.UserRepository.getUserbyUsername(username);
		
		org.springframework.data.domain.Pageable pageable =  PageRequest.of(page, 5); 
		
		Page<Contact> contacts = this.ContactRepository.findContactbyUser(user1.getId(), pageable);
		
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		
		m.addAttribute("totalPages", contacts.getTotalPages());
		
		return "normal/show_contacts";
	}
	
	
	/** Show respective contact details */
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/{cID}/contact")
	public String showContact(@PathVariable("cID") int cId, Principal principal, Model model ) {
		System.out.println("CID : "+cId);
		
		String currentUser = principal.getName();
		model.addAttribute("title", "Contact details : Smart contact Manager");
		
		Optional <Contact> contactoptional = this.ContactRepository.findById(cId);
		Contact contact = contactoptional.get();
		
		User1 user1 = this.UserRepository.getUserbyUsername(currentUser);
		
		if(user1.getId() == contact.getUser().getId())
		{
			model.addAttribute("contact", contact);
		}
		 
		
		return "normal/show_user_contact_details";
	}
	
	/** deleting contact from given user lists */
	@GetMapping("/delete/{cID}")
	public String deleteContact(@PathVariable("cID") Integer cID, Principal principal, Model model ) {
		String currentUser = principal.getName();
		
		Optional<Contact> contactoptional = this.ContactRepository.findById(cID);
		Contact contact = contactoptional.get();
		
        contact.setUser(null);
      
		this.ContactRepository.delete(contact);
		
		
		 
		return "redirect:/user/show-contacts/0";
	}

}
