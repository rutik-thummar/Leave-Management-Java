package com.leavemanagement.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "emp_leave")
public class EmployeeLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	Date fromDate;
	Date toDate;
	String reason;
	String status;
	String type;
	String slot;

//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne(cascade = CascadeType.MERGE)
	Employee employee;

	@Override
	public String toString() {
		return "EmployeeLeave [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", reason=" + reason
				+ ", status=" + status + ", type=" + type + ", slot=" + slot + "]";
	}

	

}
