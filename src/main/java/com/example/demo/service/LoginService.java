package com.example.demo.service;

import java.security.KeyPair;
import java.time.Instant;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.controller.login.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.util.TokenUtils;

@Service
public class LoginService {

	private final KeyPair keyPair;
	private final UserService userService;

	public LoginService(KeyPair keyPair, UserService userService) {
		this.keyPair = keyPair;
		this.userService = userService;
	}

	public LoginResponse generateToken(UserDetails user) {
		return new LoginResponse("acess", "request");
	}

	public LoginResponse generateToken(User user) {
		Instant now = Instant.now();
		String accessToken = TokenUtils.generateAccesToken(user, keyPair.getPrivate(), now);
		String refreshToken = TokenUtils.generateRefreshToken(user, keyPair.getPrivate(), now);
		return new LoginResponse(accessToken, refreshToken);
	}

	public LoginResponse refreshToken(String refreshToken) {
		var optJws = TokenUtils.parseToken(refreshToken, keyPair.getPublic());
		if (!optJws.isPresent()) {
			throw new AccessDeniedException("Invalid refresh token");
		}
		var claims = optJws.get().getPayload();
		if (!claims.get("type").equals("refresh")) {
			throw new AccessDeniedException("Invalid refresh token");
		}
		var user = userService.loadUserByUsername(claims.getSubject());
		return generateToken(user);
	}

}
