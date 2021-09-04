package com.leantech.javatest.dto;

import lombok.Data;

@Data
public class EmployeeResponseDto {
	private String fullName;
	private String address;
	private String cellphone;
	private String cityName;
	private String positionName;
	private double salary;
	private String status;
	private long id;

}
