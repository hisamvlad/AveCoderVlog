package com.avecoder.avecoder.avecoderblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avecoder.avecoder.avecoderblog.dto.LoginRequest;
import com.avecoder.avecoder.avecoderblog.dto.RegisterRequest;
import com.avecoder.avecoder.avecoderblog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/signup")
	//Response Entity - built in class to send response to the client
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
}
