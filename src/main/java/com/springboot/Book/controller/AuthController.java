package com.springboot.Book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.Book.JwtUtil;
import com.springboot.Book.dto.JwtDto;
import com.springboot.Book.dto.ResponseMessageDto;
import com.springboot.Book.exception.InvalidUsernameException;
import com.springboot.Book.model.User;
import com.springboot.Book.service.UserSecurityService;
import com.springboot.Book.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSecurityService userSecurityService;

	@PostMapping("/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody User user, ResponseMessageDto dto) {
		try {
			return ResponseEntity.ok(userService.signup(user));
		} catch (InvalidUsernameException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

	@PostMapping("/api/generateToken")
	public ResponseEntity<?> getToken(@RequestBody User user, JwtDto dto) {
		try {
			Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

			authenticationManager.authenticate(auth);
			user = (User) userSecurityService.loadUserByUsername(user.getUsername());

			String jwt = jwtUtil.generateToken(user.getUsername());
			dto.setUsername(user.getUsername());
			dto.setToken(jwt);
			return ResponseEntity.ok(dto);
		} catch (AuthenticationException ae) {
			return ResponseEntity.badRequest().body(ae.getMessage());
		}
	}

}
