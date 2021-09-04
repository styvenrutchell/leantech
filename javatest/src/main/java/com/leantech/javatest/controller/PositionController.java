package com.leantech.javatest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leantech.javatest.dto.PositionResponseDto;
import com.leantech.javatest.service.PositionService;

@RestController
@RequestMapping("api/v1/positions")
public class PositionController {
	
	@Autowired
	private PositionService positionService;
	
	@GetMapping("/best-salaries")
	public ResponseEntity<List<PositionResponseDto>> getPositionsOrderedBySalary(){
		List<PositionResponseDto> bestPositions = positionService.getPositionsOrderedBySalary();
		 
		return ResponseEntity.ok(bestPositions);
	}
	
}
