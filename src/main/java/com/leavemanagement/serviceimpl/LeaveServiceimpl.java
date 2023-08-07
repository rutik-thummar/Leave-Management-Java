package com.leavemanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.model.Employee;
import com.leavemanagement.model.EmployeeLeave;
import com.leavemanagement.repository.EmployeeRepository;
import com.leavemanagement.repository.LeaveRepository;
import com.leavemanagement.service.LeaveService;

@Service
public class LeaveServiceimpl implements LeaveService {

	@Autowired
	LeaveRepository leaveRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired(required = true)
	ModelMapper modelMapper;

	@Override
	public List<EmployeeLeave> getAll(EmployeeLeaveDTO leaveDTO) {
		try {
		Employee employee = employeeRepository.findByEmail(leaveDTO.getEmail().replace("\"", ""));
		return leaveRepository.findByEmployeeIdOrderByFromDateDesc(employee.getId());
		}catch(Exception e) {
//			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean addLeave(EmployeeLeaveDTO leaveDTO) {
		try {
			Employee employee = employeeRepository.findByEmail(leaveDTO.getEmail().replace("\"", ""));
			EmployeeLeave employeeLeave = new EmployeeLeave();
			modelMapper.map(leaveDTO, employeeLeave);
			employeeLeave.setEmployee(employee);
			employeeLeave.setStatus(leaveDTO.getStatus());
			if (employeeLeave.getType().equalsIgnoreCase("Full")) {
				employeeLeave.setSlot(null);
			}
			leaveRepository.save(employeeLeave);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public EmployeeLeave getLeave(int id) {
		return leaveRepository.findById(id).get();
	}

	@Override
	public Boolean deleteLeave(int id) {
		try {
			leaveRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<EmployeeLeave> getLeaveByName(String name) {
		Employee employee = employeeRepository.findByFirstName(name);
		return leaveRepository.findByEmployeeIdOrderByFromDateDesc(employee.getId());
	}

	@Override
	public List<EmployeeLeave> getLeaveByEmail(String email) {
		Employee employee = employeeRepository.findByEmail(email.replace("\"", ""));
		return leaveRepository.findByEmployeeIdOrderByFromDateDesc(employee.getId());
	}

	@Override
	public Boolean updateLeave(EmployeeLeave employeeLeave) {
		try {
			if (employeeLeave.getType().equalsIgnoreCase("Full")) {
				employeeLeave.setSlot(null);
			}
			leaveRepository.save(employeeLeave);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateLeaveStatus(int id, EmployeeLeave employeeLeave) {
		try {
			EmployeeLeave leave = leaveRepository.findById(id).get();
			leave.setStatus(employeeLeave.getStatus());
			leaveRepository.save(leave);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<EmployeeLeaveDTO> getPendingLeave() {
		List<EmployeeLeave> leaves = leaveRepository.findAll();
		List<EmployeeLeaveDTO> leavess = leaves.stream().map(t -> {
			EmployeeLeaveDTO leave = new EmployeeLeaveDTO();
			modelMapper.map(t, leave);
			leave.setName(t.getEmployee().getFirstName() + " " + t.getEmployee().getLastName());
			return leave;
		}).collect(Collectors.toList());
		List<EmployeeLeaveDTO> leaveList = new ArrayList<>();
		for (EmployeeLeaveDTO leave : leavess) {
			if (leave.getStatus().equalsIgnoreCase("Pending")) {
				leaveList.add(leave);
			}
		}
		return leaveList;
	}

	@Override
	public List<EmployeeLeaveDTO> getLeave() {
		List<EmployeeLeave> leaves = leaveRepository.findAll();
		List<EmployeeLeaveDTO> leavess = leaves.stream().map(t -> {
			EmployeeLeaveDTO leave = new EmployeeLeaveDTO();
			modelMapper.map(t, leave);
			leave.setName(t.getEmployee().getFirstName() + " " + t.getEmployee().getLastName());
			return leave;
		}).collect(Collectors.toList());
		List<EmployeeLeaveDTO> leaveList = new ArrayList<>();
		for (EmployeeLeaveDTO leave : leavess) {
			if (!leave.getStatus().equalsIgnoreCase("Pending")) {
				leaveList.add(leave);
			}
		}
		return leaveList;
	}

}
