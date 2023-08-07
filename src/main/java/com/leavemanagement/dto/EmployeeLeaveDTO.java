package com.leavemanagement.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class EmployeeLeaveDTO {

	int id;
	Date fromDate;
	Date toDate;
	String reason;
    String email;
    String name;
	String status; 
	String type;
	String slot;

}

