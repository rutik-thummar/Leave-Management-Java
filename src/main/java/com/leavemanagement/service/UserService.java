package com.leavemanagement.service;

import com.leavemanagement.model.User;


public interface UserService {

	Boolean userRegister(User user);

	Boolean adminRegister(User user);
	
	Boolean login(User user);
	
	User getUserByEmail(String email);
}
