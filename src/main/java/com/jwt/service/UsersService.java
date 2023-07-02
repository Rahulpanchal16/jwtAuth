package com.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.entity.Users;

@Service
public class UsersService {

	private List<Users> users = new ArrayList<>();

	public UsersService() {
		users.add(new Users(UUID.randomUUID().toString(), "Virendra Sehwag", "Mumbai"));
		users.add(new Users(UUID.randomUUID().toString(), "Rahul Dravid", "Mumbai"));
		users.add(new Users(UUID.randomUUID().toString(), "Yuvraj Singh", "Pune"));
		users.add(new Users(UUID.randomUUID().toString(), "Anil Kumble", "Kolhapur"));
	}

	public List<Users> getAllUsers() {
		return this.users;
	}

}
