package com.example.demo.filter;

import java.io.IOException;
import java.security.KeyPair;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BearerTokenFilter extends OncePerRequestFilter {

	private final KeyPair keypair;
	private final UserService userService;

	public BearerTokenFilter(KeyPair keypair, UserService userService) {
		this.keypair = keypair;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = (String) request.getHeader("Authorization");

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		var optJws = TokenUtils.parseToken(token, keypair.getPublic());

		if (!optJws.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}

		Claims claims = optJws.get().getPayload();
		if (claims.get("type").equals("access")) {
			String email = claims.getSubject();
			var user = userService.loadUserByUsername(email);
			var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			authToken.setDetails(user);
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);

	}

}