package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private final UserRepository userRepository;

	public LoginService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
}
