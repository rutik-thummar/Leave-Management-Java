package com.leavemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.model.EmployeeLeave;
import com.leavemanagement.service.LeaveService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "leave")
public class LeaveController {

	@Autowired
	LeaveService leaveService;
	
	@PostMapping(value = "")
	public List<EmployeeLeave> getAll(@RequestBody EmployeeLeaveDTO leave){
		return leaveService.getAll(leave);
	}
	@GetMapping(value = "getleave")
	public List<EmployeeLeaveDTO> getList(){
		return leaveService.getLeave();
	}
	@GetMapping(value = "getPendingLeave")
	public List<EmployeeLeaveDTO> getPendingLeave(){
		return leaveService.getPendingLeave();
	}
	
	@PostMapping(value = "add")	
	public ResponseEntity<String> addLeave(@RequestBody EmployeeLeaveDTO leave) {
		Boolean flag = leaveService.addLeave(leave);
		if (Boolean.TRUE.equals(flag)) {
			return ResponseEntity.status(HttpStatus.OK).body("Added Successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed! Please Try Again...");
		}
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<EmployeeLeave> getLeave(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK).body(leaveService.getLeave(id));
	}
	
	@PutMapping(value = "update")	
	public ResponseEntity<String> updateleave(@RequestBody EmployeeLeave leave) {
		Boolean flag = leaveService.updateLeave(leave);
		if (Boolean.TRUE.equals(flag)) {
			return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed! Please Try Again...");
		}
	}
	@PutMapping(value = "update/{id}")	
	public ResponseEntity<String> updateleaveStatus(@PathVariable(value = "id") int id,@RequestBody EmployeeLeave leave) {
				Boolean flag = leaveService.updateLeaveStatus(id,leave);
		if (flag) {
			return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed! Please Try Again...");
		}

	}
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteLeave(@PathVariable int id) {
		leaveService.deleteLeave(id);
		return ResponseEntity.status(HttpStatus.OK).body("Leave Deleted Successfully.");
	}
	@GetMapping(path = "")
	public List<EmployeeLeave> getReport(@RequestParam("name") String name) {
		return leaveService.getLeaveByName(name);
	}
	
	@GetMapping(path = "/get")
	public List<EmployeeLeave> getReportByEmail(@RequestParam("email") String email) {
		return leaveService.getLeaveByEmail(email);
	}
	


}
