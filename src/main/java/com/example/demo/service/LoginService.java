package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.login.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private final UserRepository userRepository;

	public LoginService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public Object generateToken(User user) {
		// TODO Auto-generated method stub
		return new LoginResponse("acess", "request");
	}
	
}
