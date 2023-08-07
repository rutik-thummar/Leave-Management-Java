package com.leavemanagement.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leavemanagement.model.Employee;
import com.leavemanagement.model.User;
import com.leavemanagement.repository.EmployeeRepository;
import com.leavemanagement.repository.UserRepository;
import com.leavemanagement.service.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeRepository empRepository;

	@Override
	public Boolean userRegister(User user) {
		User email = userRepository.findByEmail(user.getEmail());
		Employee emp = empRepository.findByEmail(user.getEmail());
		if (email != null || emp == null) {
			return false;
		}
		user.setEmployee(emp);
		userRepository.save(user);
		return true;
	}

	@Override
	public Boolean adminRegister(User user) {
		User email = userRepository.findByEmail(user.getEmail());
		Employee emp = empRepository.findByEmail(user.getEmail());
		if (email != null || emp != null) {
			return false;
		}
		userRepository.save(user);
		return true;
	}

	@Override
	public Boolean login(User user) {
		try {
			User existingUser = userRepository.findByEmail(user.getEmail());
			if (existingUser != null && user.getPassword().contentEquals(existingUser.getPassword())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
