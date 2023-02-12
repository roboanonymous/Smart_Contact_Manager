package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MyConfig {

	@Bean
	public UserDetailsService UserDetailsService()
	{
		return new UserDetailSrviceImpel();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider DaoAuthenticationProvider = new DaoAuthenticationProvider();
		DaoAuthenticationProvider.setUserDetailsService(this.UserDetailsService());
		DaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return DaoAuthenticationProvider;
	}
	
	
	// Configure Method ... 
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	
	@Bean
	public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception
	{
		http.
		authorizeHttpRequests()
		.requestMatchers("/admin/**").hasRole("ADMIN")
	    .requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/**").permitAll().and()
		.formLogin(form -> form
				.loginPage("/signin")
				.permitAll()
			)
		.csrf().disable();
		
		http.authenticationProvider(authenticationProvider());
		
		DefaultSecurityFilterChain build = http.build();
		
		return build;
	}
	
	
	
	
}
