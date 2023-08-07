package com.leavemanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leavemanagement.model.Employee;
import com.leavemanagement.service.EmployeeService;

@SpringBootTest
class EmployeeTest {
	
	@Autowired
	private EmployeeService EmployeeService;

	@Test
	void getAllPersonTest() {
		boolean actualResult = false;
		List<Employee> EmployeeList = EmployeeService.getAll();
		if (EmployeeList.size() != 0) {
			actualResult = true;
		}
		assertThat(actualResult).isTrue();
	}

//	@Test
	void addEmployeeTest() {
		Employee emp = new Employee();
		emp.setFirstName("Testing");
		emp.setLastName(" 123 ");
		emp.setJoiningDate("2023-08-01");
		emp.setMobileNo("9898898989");
		emp.setGender("Male");
		emp.setEmail("testing@gmail.com");
		emp.setSalary(15000);
		emp.setDesignation("Developer");
		Boolean flag = EmployeeService.addEmployee(emp);
		assertThat(flag).isTrue();
	}

	@Test
	void updateEmployeeTest() {
		Employee emp = new Employee();
		emp.setId(115);
		emp.setFirstName("Test");
		emp.setLastName(" 1 ");
		emp.setJoiningDate("2023-08-01");
		emp.setMobileNo("1213412345");
		emp.setGender("Female");
		emp.setEmail("test@gmail.com");
		emp.setSalary(15000);
		emp.setDesignation("Developer");
		Boolean flag = EmployeeService.updateEmployee(emp);
		assertThat(flag).isTrue();
	}

	@Test
	void getEmployeeTest() {
		Employee getEmployee = EmployeeService.getEmployee(115);
		assertThat(getEmployee.getEmail()).isEqualTo("test@gmail.com");
	}

	@Test
	void getEmployeeByNameTest() {
		Employee getEmployeeByName = EmployeeService.getEmployeeByName("Test");
		assertThat(getEmployeeByName.getEmail()).isEqualTo("test@gmail.com");
	}

	void deleteEmployeeTest() {
		EmployeeService.deleteEmployee(115);
	}

	@Test
	void getEmployeeByEmailTest() {
		Employee getEmployee = EmployeeService.getEmployeeByEmail("test@gmail.com");
		assertThat(getEmployee.getFirstName()).isEqualTo("Test");
	}
}
