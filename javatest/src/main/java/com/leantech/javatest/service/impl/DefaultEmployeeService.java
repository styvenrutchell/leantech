package com.leantech.javatest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leantech.javatest.dto.CandidateDto;
import com.leantech.javatest.dto.EmployeeRequestDto;
import com.leantech.javatest.dto.EmployeeResponseDto;
import com.leantech.javatest.dto.EmployeeSearchRequestDto;
import com.leantech.javatest.exception.EmployeeNotFoundException;
import com.leantech.javatest.model.Candidate;
import com.leantech.javatest.model.Employee;
import com.leantech.javatest.model.Position;
import com.leantech.javatest.repository.EmployeeRepository;
import com.leantech.javatest.service.EmployeeService;
import com.leantech.javatest.util.EmployeeStatus;

@Service
public class DefaultEmployeeService implements EmployeeService{
	private EmployeeRepository employeeRepository;

	@Autowired
	public DefaultEmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}



	@Override
	public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeDto) {
		Employee employee = employeeDtoToEntity(employeeDto);
		employee.setStatus(EmployeeStatus.ACTIVE);
		Employee employeeResponse = employeeRepository.save(employee);
		return fromEntityToEmployeeDto(employeeResponse);
	}
	
	@Override
	public List<EmployeeResponseDto> getActiveEmployees() {
		List<Employee> employeeList = employeeRepository.findByStatus(EmployeeStatus.ACTIVE);
		List<EmployeeResponseDto> employeeDtoList = employeeList.stream().map(e -> fromEntityToEmployeeDto(e)).collect(Collectors.toList());
		return employeeDtoList;
	}
	
	@Override
	public EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeDto) {
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeDto.getId());
		if(employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			if(employeeHasFieldsToUpdate(employee,employeeDto)) {
				updateEmployeeData(employee, employeeDto);
				Employee employeeUpdated = employeeRepository.save(employee);
				return fromEntityToEmployeeDto(employeeUpdated);
			}
			
		}
		throw new EmployeeNotFoundException(employeeDto.getId());
	}
	
	private void updateEmployeeData(Employee employee, EmployeeRequestDto employeeDto) {
		Candidate candidate = employee.getCandidate();
		candidate.setName(employeeDto.getCandidate().getName());
		candidate.setLastName(employeeDto.getCandidate().getLastName());
		candidate.setAddress(employeeDto.getCandidate().getAddress());
		candidate.setCellphone(employeeDto.getCandidate().getCellphone());
		candidate.setCityName(employeeDto.getCandidate().getCityName());
		employee.setCandidate(candidate);
		employee.setSalary(employeeDto.getSalary());
		Position position = employee.getPosition();
		position.setName(employeeDto.getPosition().getName());
		employee.setPosition(position);
		
	}



	private boolean employeeHasFieldsToUpdate(Employee employee, EmployeeRequestDto employeeDto) {
		
		if(!employee.getCandidate().getName().equals(employeeDto.getCandidate().getName())) {
			return true;
		}
		if(!employee.getCandidate().getLastName().equals(employeeDto.getCandidate().getLastName())) {
			return true;
		}
		if(!employee.getCandidate().getAddress().equals(employeeDto.getCandidate().getAddress())) {
			return true;
		}
		if(!employee.getCandidate().getCellphone().equals(employeeDto.getCandidate().getCellphone())) {
			return true;
		}
		if(!employee.getCandidate().getCityName().equals(employeeDto.getCandidate().getCityName())) {
			return true;
		}
		if(employee.getSalary() != employeeDto.getSalary()) {
			return true;
		}
		if(!employee.getPosition().getName().equals(employeeDto.getPosition().getName())) {
			return true;
		}
		
		return false;
	}



	@Override
	public void deleteEmployeeById(long employeeId) {
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		if(employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			employee.setStatus(EmployeeStatus.DELETED);
			employeeRepository.save(employee);
		}
		
	}
	
	@Override
	public List<EmployeeResponseDto> getActiveEmployeesByPositionAndName(EmployeeSearchRequestDto employee) {
		if(StringUtils.isNotBlank(employee.getName())) {
			if(StringUtils.isNotBlank(employee.getPositionName())) {
				List<Employee> employeeList =  employeeRepository.findByCandidateNameAndPositionName(employee.getName(),employee.getPositionName());
				return employeeList.stream().map(e -> fromEntityToEmployeeDto(e)).collect(Collectors.toList());
			}
			List<Employee> employeeList =  employeeRepository.findByCandidateName(employee.getName());
			return employeeList.stream().map(e -> fromEntityToEmployeeDto(e)).collect(Collectors.toList());
		} else if(StringUtils.isNotBlank(employee.getPositionName())) {
			List<Employee> employeeList =  employeeRepository.findByPositionName(employee.getPositionName());
			return employeeList.stream().map(e -> fromEntityToEmployeeDto(e)).collect(Collectors.toList());
		}
		return null;
	}
	
	private Employee employeeDtoToEntity(EmployeeRequestDto dto) {
		return Employee.builder()
								.candidate(candidateDtoToEntity(dto.getCandidate()))
								.position(Position.builder().name(dto.getPosition().getName()).build())
								.salary(dto.getSalary())
								.build();
	}
	
	private Candidate candidateDtoToEntity(CandidateDto dto) {
		return Candidate.builder()
								.address(dto.getAddress())
								.name(dto.getName())
								.lastName(dto.getLastName())
								.cellphone(dto.getCellphone())
								.cityName(dto.getCityName())
								.build();
	}
	
	private EmployeeResponseDto fromEntityToEmployeeDto (Employee employee) {
		EmployeeResponseDto employeeDto = new EmployeeResponseDto();
		employeeDto.setFullName(getFullName(employee.getCandidate().getName(), employee.getCandidate().getLastName()));
		employeeDto.setCellphone(employee.getCandidate().getCellphone());
		employeeDto.setAddress(employee.getCandidate().getAddress());
		employeeDto.setCityName(employee.getCandidate().getCityName());
		employeeDto.setPositionName(employee.getPosition().getName());
		employeeDto.setSalary(employee.getSalary());
		employeeDto.setStatus(employee.getStatus().name());
		employeeDto.setId(employee.getId());
		return employeeDto;
	}
	
	private String getFullName(String name, String lastName) {
		StringBuilder fullName = new StringBuilder();
		fullName.append(name);
		fullName.append(" ");
		fullName.append(lastName);
		return fullName.toString();
	}

}
