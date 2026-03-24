package com.icalonics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.icalonics.model.entity.Register;
import com.icalonics.model.security.JwtUtil;

@RestController
public class LoginController {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Register register) {

		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(register.getUsername(), register.getPassword()));

		if (authentication.isAuthenticated()) {
			return Map.of("token", jwtUtil.generateToken(register.getUsername()));
		}

		throw new BadCredentialsException("Invalid username or password");
	}
}