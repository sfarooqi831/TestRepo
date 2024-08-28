package com.alivia.bussiness.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity extends WebSecurityConfigurerAdapter {
//	
//	
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder; 
//	
//	@Autowired 
//	DataSource dataSource;
//	
//	// Authorization
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	/**	http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//		http.formLogin();
//		http.httpBasic();*/
//		
//		http.authorizeHttpRequests()
//		.antMatchers("/statment").authenticated()
//		.antMatchers("/balance").hasAuthority("USER")
//		.antMatchers("/loan").authenticated()
//		.antMatchers("/welcome").hasAnyAuthority("USER")
//		.antMatchers(HttpMethod.POST, "/welcome").hasAuthority("USER")
//		.antMatchers("/contactus").permitAll()
//		.antMatchers("/loanProcess").hasAnyAuthority("ADMIN","USER","MANAGER")
//		.anyRequest().authenticated()
//		// Login Form details if the request comes form the browser then use this 
//		.and().formLogin()
//		.defaultSuccessUrl("/app", true)
//		.and()
//		// Other than browser
//		.httpBasic()
//		.and()
//		// provide default logout
//		.logout()
//		.and()
//		.exceptionHandling()
//		.accessDeniedPage("/denied")
//		;
//	}
//	
//	
//	// Authentication
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//  
//  		auth.jdbcAuthentication()
//		// create DB Connection
//		.dataSource(dataSource)
//		.usersByUsernameQuery("select uname,upwd,enabled from userTab where uname=?")
//	    .authoritiesByUsernameQuery("select uname, concat('ROLE_', urol) from userTab where uname=?")
//		.passwordEncoder(bCryptPasswordEncoder);
//		
//		
//		/*       ************* In memory Authentication ***********
//		
//		// for saving plain text  		.withUser("yasir").password("{noop}yasir").authorities("admin").and()
//		auth.inMemoryAuthentication()
//		.withUser("yasir").password("yasir").authorities("admin").and()
//		.withUser("faheem").password("faheem").authorities("user").and()
//		.withUser("usama").password("usama").authorities("user").and()
//		.withUser("sajid").password("sajid").authorities("manager").and()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());
//		
//		*/
//	}
//

//}
