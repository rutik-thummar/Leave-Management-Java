package com.leavemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.service.EmailService;

@RestController
@CrossOrigin
@RequestMapping(path = "sendEmail")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping(value = "")
	public void sendMail(@RequestBody EmployeeLeaveDTO employeeLeaveDTO) {
		emailService.sendMail(employeeLeaveDTO);
	}
}
