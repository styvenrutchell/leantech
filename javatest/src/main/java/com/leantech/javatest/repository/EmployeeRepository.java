package com.leantech.javatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leantech.javatest.model.Employee;
import com.leantech.javatest.util.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	List<Employee> findByStatus(EmployeeStatus status);
	List<Employee> findByCandidateName(String name);
	List<Employee> findByPositionName(String name);
	List<Employee> findByCandidateNameAndPositionName(String candidateName, String positionName);
	List<Employee> findByOrderBySalaryDesc();

}
