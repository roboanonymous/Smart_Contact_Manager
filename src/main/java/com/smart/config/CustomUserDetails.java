package com.smart.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entities.User1;

public class CustomUserDetails implements UserDetails {
	
	private User1 user1;
	

	public CustomUserDetails(User1 user1) {
		super();
		this.user1 = user1;
	}

	public User1 getUser1() {
		return user1;
	}

	public void setUser1(User1 user1) {
		this.user1 = user1;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		SimpleGrantedAuthority SimpleGrantedAuthority = new SimpleGrantedAuthority (user1.getRole());
		
		return List.of(SimpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		
		return user1.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user1.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
