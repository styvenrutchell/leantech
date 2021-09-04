package com.leantech.javatest.exception;

public class EmployeeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmployeeNotFoundException (long id) {
		super("Employee with id " + id +" does not exist");
	}

}
