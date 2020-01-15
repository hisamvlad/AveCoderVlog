package com.avecoder.avecoder.avecoderblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avecoder.avecoder.avecoderblog.dto.LoginRequest;
import com.avecoder.avecoder.avecoderblog.dto.RegisterRequest;
import com.avecoder.avecoder.avecoderblog.model.User;
import com.avecoder.avecoder.avecoderblog.repository.UserRepository;
import com.avecoder.avecoder.avecoderblog.security.JwtProvider;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;
	
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUserName(registerRequest.getUsername());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		//saves user to Db
		userRepository.save(user);
	}
	
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public String login(LoginRequest loginRequest) {
		Authentication authenticate = 
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		jwtProvider.generateToken(authenticate);
		return jwtProvider.generateToken(authenticate);
	}

	public Optional <org.springframework.security.core.userdetails.User> getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = 
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.
				getContext().getAuthentication().getPrincipal();
		return Optional.of(principal);
	}
}
