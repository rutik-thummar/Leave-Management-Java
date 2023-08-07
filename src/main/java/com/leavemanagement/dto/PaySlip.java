package com.leavemanagement.dto;

public class PaySlip {

	long id;
	String monthOrYear;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMonthOrYear() {
		return monthOrYear;
	}

	public void setMonthOrYear(String monthOrYear) {
		this.monthOrYear = monthOrYear;
	}

	@Override
	public String toString() {
		return "PaySlip [id=" + id + ", monthOrYear=" + monthOrYear + "]";
	}

}
