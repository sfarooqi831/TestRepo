package com.alivia.bussiness.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class AppSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	UserDetailsService userDetailsService;

	
	@Autowired
	InvalidUserAuthenticationEntryPoint authenticationEntryPoint;
	
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	/**
	 * Authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(encoder);
		System.out.println("Implmentation used " + userDetailsService.getClass().getCanonicalName());
	}
	
	/**
	 * After spring Boot 3.x there is the change
	 */
	
	@Bean
	public AuthenticationManager authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder);
		provider.setUserDetailsService(userDetailsService);
		return new ProviderManager(provider);
	}
	
	

	/**
	 * Authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.antMatchers("/user/save","/user/login").permitAll()
		.antMatchers("/user/report").hasAnyAuthority("ADMIN")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		// if the user access without login
//		.authenticationEntryPoint((req,res,ex)->{
//			res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized User..!");
//		})
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// 2nd request on words
		.and()
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
