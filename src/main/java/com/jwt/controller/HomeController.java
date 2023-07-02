package com.jwt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.Users;
import com.jwt.service.UsersService;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

	@Autowired
	private UsersService service;

	// localhost:8081/api/login/user
	@GetMapping(path = "/user")
	public List<Users> user() {
		System.out.println("Getting users");
		return this.service.getAllUsers();
	}

	@GetMapping(path = "/current-user")
	public String currentUser(Principal principal) {
		return principal.getName();
	}

}
