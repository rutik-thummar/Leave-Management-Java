package com.leavemanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.model.Employee;
import com.leavemanagement.model.EmployeeLeave;
import com.leavemanagement.service.LeaveService;

@SpringBootTest
public class LeaveServiceTest {

	@Autowired
	LeaveService leaveService;

	@Test
	void getAllPersonTest() {
		boolean actualResult = false;
		EmployeeLeaveDTO leave = new EmployeeLeaveDTO();
		leave.setEmail("user@gmail.com");
		List<EmployeeLeave> leaveList = leaveService.getAll(leave);
		if (leaveList.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}

	@Test
	void getPendingLeaveTest() {
		boolean actualResult = false;
		List<EmployeeLeaveDTO> getPendingLeave = leaveService.getPendingLeave();

		if (getPendingLeave.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}

	@Test
	void getLeaveTest() {
		boolean actualResult = false;
		List<EmployeeLeaveDTO> getLeave = leaveService.getLeave();

		if (getLeave.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}

//	@Test
	void addLeaveTest() throws ParseException {
		EmployeeLeaveDTO emp = new EmployeeLeaveDTO();
		LocalDate localDate = LocalDate.of(2014, 9, 11);
		Date date = Date.valueOf(localDate);
		emp.setFromDate(date);
		emp.setToDate(date);
		emp.setReason("Testing");
		emp.setEmail("user@gmail.com");
		emp.setStatus("Pending");
		emp.setType("Full");
		emp.setSlot("Morning");
		boolean addLeave = leaveService.addLeave(emp);
		assertThat(addLeave).isTrue();
	}

//	@Test
	void updateLeaveTest() throws ParseException {
		EmployeeLeave leave = new EmployeeLeave();
		leave.setId(110);
		LocalDate localDate = LocalDate.of(2014, 9, 11);
		Date date = Date.valueOf(localDate);
		leave.setFromDate(date);
		leave.setToDate(date);
		Employee emp = new Employee();
		emp.setId(81);
		leave.setEmployee(emp);
		leave.setReason("Testing123");
		leave.setStatus("Pending");
		leave.setType("Full");
		leave.setSlot("Morning");
		boolean addLeave = leaveService.updateLeave(leave);
		assertThat(addLeave).isTrue();
	}

	@Test
	void updateLeaveStatusTest() {
		EmployeeLeave empLeave = new EmployeeLeave();
		empLeave.setStatus("Approved");
		boolean updateLeaveStatus = leaveService.updateLeaveStatus(110, empLeave);
		assertThat(updateLeaveStatus).isTrue();

	}

	@Test
	void getLeaveByIdTest() {
		EmployeeLeave getLeave = leaveService.getLeave(110);
		assertThat(getLeave.getStatus()).isEqualTo("Approved");
	}

	@Test
	void getLeaveByNameTest() {
		boolean actualResult = false;

		List<EmployeeLeave> getLeaveByName = leaveService.getLeaveByName("Rutik");
		if (getLeaveByName.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}

//	@Test
	void deleteLeaveTest() {
		boolean deleteLeave = leaveService.deleteLeave(111);
		assertThat(deleteLeave).isTrue();
	}

	@Test
	void getLeaveByEmailTest() {
		boolean actualResult = false;

		List<EmployeeLeave> getLeaveByEmail = leaveService.getLeaveByEmail("user@gmail.com");
		if (getLeaveByEmail.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}
}
