package com.leavemanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeaveManagementApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LeaveManagementApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
	 return new ModelMapper();
	}
}
