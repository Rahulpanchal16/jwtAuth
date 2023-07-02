package com.jwt.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("Authorization");

		String username = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);

			try {
				username = this.jwtHelper.extractUsername(token);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println("Illegal argument while fetching the username");
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("given jwt token is expired");
			} catch (MalformedJwtException e) {
				e.printStackTrace();
				System.out.println("Invalid jwt token");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Invalid header value...");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(auth);

			} else {
				System.out.println("Validation Failed...");
			}

		}

		filterChain.doFilter(request, response);

	}

}
