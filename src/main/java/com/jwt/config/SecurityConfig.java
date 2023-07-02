package com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.security.JwtAuthenticationEntryPoint;
import com.jwt.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	public SecurityFilterChain filterChail(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers("/home/**").authenticated()
		.requestMatchers("/auth/login").permitAll().anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

}
