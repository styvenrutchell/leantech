package com.leantech.javatest.service;

import java.util.List;

import com.leantech.javatest.dto.EmployeeRequestDto;
import com.leantech.javatest.dto.EmployeeResponseDto;
import com.leantech.javatest.dto.EmployeeSearchRequestDto;

public interface EmployeeService {
	
	EmployeeResponseDto createEmployee(EmployeeRequestDto employee);
	List<EmployeeResponseDto> getActiveEmployees();
	EmployeeResponseDto updateEmployee(EmployeeRequestDto employee);
	void deleteEmployeeById(long employeeId);
	List<EmployeeResponseDto> getActiveEmployeesByPositionAndName(EmployeeSearchRequestDto employee);

}
