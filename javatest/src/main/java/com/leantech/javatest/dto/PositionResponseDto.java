package com.leantech.javatest.dto;

import java.util.List;

import lombok.Data;

@Data
public class PositionResponseDto {
	private long id;
	private String name;
	private List<EmployeeDto> employees;

}
