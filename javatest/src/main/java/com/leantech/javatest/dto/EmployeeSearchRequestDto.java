package com.leantech.javatest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeSearchRequestDto {
	private String name;
	private String positionName;

	public EmployeeSearchRequestDto(String name, String positionName) {
		this.name = name;
		this.positionName = positionName;
	}
}
