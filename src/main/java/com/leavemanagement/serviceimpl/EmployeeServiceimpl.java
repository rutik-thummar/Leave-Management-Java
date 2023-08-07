package com.leavemanagement.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leavemanagement.model.Employee;
import com.leavemanagement.repository.EmployeeRepository;
import com.leavemanagement.service.EmployeeService;

@Service
public class EmployeeServiceimpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired(required = true)
	ModelMapper modelMapper;

	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Boolean addEmployee(Employee emp) {
		try {
			Employee employee = employeeRepository.findByEmail(emp.getEmail());
			if (employee == null) {
				employeeRepository.save(emp);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Employee getEmployee(int id) {
		return employeeRepository.findById(id).get();
	}
	
	@Override
	public Employee getEmployeeByName(String name) {
		return employeeRepository.findByFirstName(name);
	}


	@Override
	public void deleteEmployee(int id) {
			employeeRepository.deleteById(id);
	}

	@Override
	public Boolean updateEmployee(Employee emp) {
		try {
			employeeRepository.save(emp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email.replace("\"", ""));
	}

}
