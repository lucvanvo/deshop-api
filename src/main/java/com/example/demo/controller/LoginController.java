package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.LoginService;

@RequestMapping("api")
@Controller
public class LoginController {
	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		super();
		this.loginService = loginService;
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(loginRequest.getUsername());
}
}