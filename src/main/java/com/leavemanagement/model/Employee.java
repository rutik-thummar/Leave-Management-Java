package com.leavemanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )	
	int id;
	String firstName;
	String lastName;
	String joiningDate;
	String mobileNo;
	String gender;	
	String email;
	int salary;
	String designation;	
	
//	@OneToMany(cascade = CascadeType.ALL )
//	List<EmployeeLeave> leave;
	

}
