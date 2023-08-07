package com.leavemanagement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leavemanagement.model.User;
import com.leavemanagement.service.UserService;
@SpringBootTest
 class UserServiceTest {
	@Autowired
	UserService userService;

//	@Test
	void getLeaveByIdTest() {
		User user=new User();
		user.setName("Test123");
		user.setEmail("test@gmail.com");
		user.setPassword("test@123");
		user.setRole("USER_ROLE");
		boolean register= userService.userRegister(user);
		assertThat(register).isTrue();
	}
	
	@Test
	void loginTest() {
		User user=new User();
		user.setName("Test123");
		user.setEmail("test@gmail.com");
		user.setPassword("test@123");
		user.setRole("USER_ROLE");
		boolean login= userService.login(user);
		assertThat(login).isTrue();
	}
	
	
	@Test
	void getUserByEmail() {
	
		User getUserByEmail= userService.getUserByEmail("test@gmail.com");
		assertThat(getUserByEmail.getPassword()).isEqualTo("test@123");
	}
}
