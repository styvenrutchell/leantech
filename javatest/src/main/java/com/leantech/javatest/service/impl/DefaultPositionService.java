package com.leantech.javatest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leantech.javatest.dto.CandidateDto;
import com.leantech.javatest.dto.EmployeeDto;
import com.leantech.javatest.dto.PositionResponseDto;
import com.leantech.javatest.model.Candidate;
import com.leantech.javatest.model.Employee;
import com.leantech.javatest.model.Position;
import com.leantech.javatest.repository.EmployeeRepository;
import com.leantech.javatest.service.PositionService;

@Service
public class DefaultPositionService implements PositionService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<PositionResponseDto> getPositionsOrderedBySalary() {
		List<PositionResponseDto> positionResponse = new ArrayList<>();
		Map<String, PositionResponseDto> positionMap = new HashMap<>();
		List<Employee> employeesOrderedBySalary = employeeRepository.findByOrderBySalaryDesc();
		
		for (int j = 0; j < employeesOrderedBySalary.size(); j++) {
			Employee currentEmployee = employeesOrderedBySalary.get(j);
			
			PositionResponseDto positionDto = getPositionIfExists(currentEmployee.getPosition().getName(), positionMap);
			if(positionDto != null) {
				positionDto.getEmployees().add(employeeFromEntityToDto(currentEmployee));
				positionMap.put(positionDto.getName(), positionDto);
			} else {
				positionDto = positionFromEntityToDto(currentEmployee.getPosition());
				List<EmployeeDto> employeesDto = new ArrayList<>();
				employeesDto.add(employeeFromEntityToDto(currentEmployee));
				positionDto.setEmployees(employeesDto);
				positionMap.put(positionDto.getName(), positionDto);
			}		
		}
		positionMap.forEach((k,v) -> positionResponse.add(v)) ;
		
		
		return positionResponse;
	}
	
	private PositionResponseDto getPositionIfExists(String positionName, Map<String, PositionResponseDto> positionMap) {	
		return positionMap.get(positionName);
	}
	

	
	private PositionResponseDto positionFromEntityToDto(Position entity) {
		PositionResponseDto dto = new PositionResponseDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		
		return dto;
		
	}
	
	private EmployeeDto employeeFromEntityToDto (Employee entity) {
		EmployeeDto dto = new EmployeeDto();
		dto.setId(entity.getId());
		dto.setSalary(entity.getSalary());
		dto.setPerson(candidateFromEntityToDto(entity.getCandidate()));
		return dto;
	}
	
	private CandidateDto candidateFromEntityToDto (Candidate entity) {
		CandidateDto dto = new CandidateDto();
		dto.setAddress(entity.getAddress());
		dto.setCellphone(entity.getCellphone());
		dto.setCityName(entity.getCityName());
		dto.setName(entity.getName());
		dto.setLastName(entity.getLastName());
		return dto;
	}


}
