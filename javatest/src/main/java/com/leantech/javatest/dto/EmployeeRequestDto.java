package com.leantech.javatest.dto;

import lombok.Data;

@Data
public class EmployeeRequestDto {
	private long id;
	private CandidateDto candidate;
	private PositionDto position;
	private double salary;

}
