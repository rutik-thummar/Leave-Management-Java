package com.leavemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leavemanagement.model.EmployeeLeave;

@Repository
public interface LeaveRepository extends JpaRepository<EmployeeLeave, Integer>{
	
	public List<EmployeeLeave> findByEmployeeIdOrderByFromDateDesc(int id);
		
}
