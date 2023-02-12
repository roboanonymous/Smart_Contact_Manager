package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entities.User1;

public class UserDetailSrviceImpel implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetch user from database
		User1 user1 = userRepository.getUserbyUsername(username);
		
		if(user1== null)
		{
			throw new UsernameNotFoundException("Could not found User");
			
		}
		
		CustomUserDetails CustomUserDetails = new CustomUserDetails(user1);
		return CustomUserDetails;
	}

}
