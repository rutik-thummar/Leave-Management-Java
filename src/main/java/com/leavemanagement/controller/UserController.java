package com.leavemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.model.User;
import com.leavemanagement.repository.UserRepository;
import com.leavemanagement.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@PostMapping(value = "register")
	public ResponseEntity<String> register(@RequestBody User user) {
		Boolean flag;
		if (user.getRole().equalsIgnoreCase("ADMIN_ROLE")) {
			flag = userService.adminRegister(user);
		} else {
			flag = userService.userRegister(user);
		}
		if (Boolean.TRUE.equals(flag)) {
			return ResponseEntity.status(HttpStatus.OK).body("Registration Successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Registration Failed! Please Try Again...");
		}
	}

	@PostMapping(value = "login")
	public ResponseEntity<String> login(@RequestBody User user) {
		Boolean flag = userService.login(user);
		if (Boolean.TRUE.equals(flag)) {
			return ResponseEntity.status(HttpStatus.OK).body("Login Successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Login Failed! Please Try Again...");
		}
	}

	@GetMapping(value = "{email}")
	public ResponseEntity<User> getUser(@PathVariable String email) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
	}
}
