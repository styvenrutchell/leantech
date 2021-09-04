package com.leantech.javatest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.JsonObject;
import com.leantech.javatest.dto.CandidateDto;
import com.leantech.javatest.dto.EmployeeRequestDto;
import com.leantech.javatest.dto.EmployeeResponseDto;
import com.leantech.javatest.dto.PositionDto;

@SpringBootTest
@WebAppConfiguration
public class EmployeeControllerTest {

	protected MockMvc mvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeEach
	private void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void createEmployeeTest() throws Exception {
		String uri = "/api/v1/employees";
		EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
		employeeRequest.setSalary(200);
		CandidateDto candidate = getTestCandidate();
		employeeRequest.setCandidate(candidate);
		employeeRequest.setPosition(getTestPosition());
		
		String inputJson = mapToJson(employeeRequest);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		JsonObject object = (JsonObject) com.google.gson.JsonParser.parseString(content);
		EmployeeResponseDto responseDto = mapFromJson(object.toString(),EmployeeResponseDto.class);
		assertEquals(200, responseDto.getSalary());
		assertEquals("Styven Palacio", responseDto.getFullName());
		
		
	}

	private PositionDto getTestPosition() {
		PositionDto position = new PositionDto();
		position.setName("testPosition");
		return position;
	}

	private CandidateDto getTestCandidate() {
		CandidateDto candidate = new CandidateDto();
		candidate.setAddress("testAddress");
		candidate.setCellphone("55555");
		candidate.setCityName("testCity");
		candidate.setName("Styven");
		candidate.setLastName("Palacio");
		return candidate;
	}
	
	private String mapToJson(Object obj) throws com.fasterxml.jackson.core.JsonProcessingException {
		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException, IOException {

		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
}
