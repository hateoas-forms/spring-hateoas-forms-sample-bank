package com.github.hateoas.forms.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.github.hateoas.forms.samples.dao.AccountDao;
import com.github.hateoas.forms.samples.security.CustomAuthenticationFailureHandler;
import com.github.hateoas.forms.samples.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountDao accountDao;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new CustomAuthenticationProvider(accountDao));
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
    	http
	        .authorizeRequests()
	        	.antMatchers("/", "/api/", "/login", "/index.html", "/scripts/**", "/styles/**", "/fonts/**").permitAll()
	            .anyRequest().authenticated()	
	            .and()
	        .formLogin()
	        	.loginPage("/login")
	        	.defaultSuccessUrl("/api/", true)
	        	.successHandler(new SimpleUrlAuthenticationSuccessHandler("/api/"))
	        	.failureHandler(new CustomAuthenticationFailureHandler("/api/?authenticationFailure=true"))
	        	.loginProcessingUrl("/j_spring_security_check")
	        	.usernameParameter("j_username")
	        	.passwordParameter("j_password")
	        	.permitAll()
	        	.and()
	        .csrf().disable()
	        .logout()
	        	.logoutUrl("/j_spring_security_logout")
	        	.logoutSuccessUrl("/api/").and()
	        .httpBasic();
    	// @formatter:on
	}

}
