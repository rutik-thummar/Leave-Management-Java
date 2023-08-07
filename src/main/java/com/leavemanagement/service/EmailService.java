package com.leavemanagement.service;

import com.leavemanagement.dto.EmployeeLeaveDTO;

public interface EmailService {

	void sendMail(EmployeeLeaveDTO employeeLeaveDTO);
}
