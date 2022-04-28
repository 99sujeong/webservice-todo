package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		if (userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("Invalied arguments");
		}
		
		final String email = userEntity.getEmail();
		
		if(userRepository.existByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("Email already exists");
		}
		
		public UserEntity getByCredentials(final String email, final String password) {
			return userRepository.findByEmailAndPassword(email, password);
		}
	}
}
