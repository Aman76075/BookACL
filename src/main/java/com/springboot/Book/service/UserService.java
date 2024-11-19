package com.springboot.Book.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.Book.exception.InvalidUsernameException;
import com.springboot.Book.model.User;
import com.springboot.Book.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public User signup(User user) throws InvalidUsernameException {
		Optional<User> optional = userRepository.findByUsername(user.getUsername());
		if (optional.isPresent()) {
			throw new InvalidUsernameException("Username already exists. Please Try Again with other username.");
		}

		String encryptedPass = passEncoder.encode(user.getPassword());
		user.setPassword(encryptedPass);

		return userRepository.save(user);
	}

}
