package com.leantech.javatest.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leantech.javatest.dto.EmployeeRequestDto;
import com.leantech.javatest.dto.EmployeeResponseDto;
import com.leantech.javatest.dto.EmployeeSearchRequestDto;
import com.leantech.javatest.service.EmployeeService;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto employeeDto){
		EmployeeResponseDto employee = employeeService.createEmployee(employeeDto);
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping
	public ResponseEntity<EmployeeResponseDto> updateEmployee(@RequestBody EmployeeRequestDto employeeDto){
		EmployeeResponseDto employee = employeeService.updateEmployee(employeeDto);
		return ResponseEntity.ok(employee);
	}
	
	@GetMapping()
	public ResponseEntity<List<EmployeeResponseDto>> getEmployees(@RequestParam(value="name", required=false) String name, @RequestParam(value="position", required = false) String position){
		List<EmployeeResponseDto> employeeList;
		if (StringUtils.isBlank(name) && StringUtils.isBlank(position)) {
			employeeList = employeeService.getActiveEmployees();
		} else {	
			EmployeeSearchRequestDto dto = new EmployeeSearchRequestDto(name,position);
			employeeList = employeeService.getActiveEmployeesByPositionAndName(dto);
		}
		 
		return ResponseEntity.ok(employeeList);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id){
		employeeService.deleteEmployeeById(id);
		ResponseEntity.ok();
	}
	

}
