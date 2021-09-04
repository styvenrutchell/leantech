package com.leantech.javatest.dto;

import lombok.Data;

@Data
public class EmployeeDto {
	private long id;
	private double salary;
	private CandidateDto person;

}
