package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	
	public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}




	@Override
	public void run(String... args) throws Exception {
		var admin = new User();
		admin.setAddress("441, Bao Bun");
		admin.setCreatedAt(LocalDateTime.now());
		admin.setEmail("admin@gmail.com");
		admin.setFullname("Admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setPhone("113");
		userRepository.save(admin);
		
	}

}
