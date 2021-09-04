package com.leantech.javatest.service;

import java.util.List;

import com.leantech.javatest.dto.PositionResponseDto;

public interface PositionService {
	List<PositionResponseDto> getPositionsOrderedBySalary();

}
