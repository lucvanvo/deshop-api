package com.example.demo.controller.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@RequestMapping("api")
@Controller
public class LoginController {
	private final LoginService loginService;
	private final AuthenticationManager authenticationManager;

	public LoginController(LoginService loginService, AuthenticationManager authenticationManager) {
		super();
		this.loginService = loginService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest = UsernamePasswordAuthenticationToken
				.unauthenticated(loginRequest.email(), loginRequest.password());
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

		if (!authenticationResponse.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not log in with " + loginRequest.email());
		}
		var user = (User) authenticationResponse.getPrincipal();
		return ResponseEntity.ok(loginService.generateToken(user));
	}
}