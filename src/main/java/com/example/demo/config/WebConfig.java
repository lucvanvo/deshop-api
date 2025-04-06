package com.example.demo.config;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.util.KeyUtils;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value ("${jwt.private-key-file}")
	private String privateKeyFile;

	@Value("${jwt.public-key-file}")
	private String publicKeyFile;

	@Bean
	KeyPair keyPair() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		var privateKey = KeyUtils.getPrivateKey(privateKeyFile);
		var publicKey = KeyUtils.getPublicKey(publicKeyFile);
		return new KeyPair(publicKey, privateKey);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:3000/");
	}

}
