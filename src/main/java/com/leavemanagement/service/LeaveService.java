package com.leavemanagement.service;

import java.util.List;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.model.EmployeeLeave;

public interface LeaveService {

	List<EmployeeLeave> getAll(EmployeeLeaveDTO employeeLeave);

	List<EmployeeLeaveDTO> getPendingLeave();

	List<EmployeeLeaveDTO> getLeave();

	Boolean addLeave(EmployeeLeaveDTO employeeLeave);

	Boolean updateLeave(EmployeeLeave employeeLeave);

	Boolean updateLeaveStatus(int id, EmployeeLeave employeeLeave);

	EmployeeLeave getLeave(int id);

	List<EmployeeLeave> getLeaveByName(String name);

	Boolean deleteLeave(int id);

	List<EmployeeLeave> getLeaveByEmail(String email);

}
