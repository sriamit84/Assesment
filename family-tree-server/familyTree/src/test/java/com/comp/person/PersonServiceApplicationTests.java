package com.comp.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.comp.familyTree.controller.PersonController;
import com.comp.familyTree.entities.Person;
import com.comp.familyTree.response.PersonResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class PersonServiceApplicationTests extends AbstractTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PersonController personController;


	@Test
	@Order(1)
	public void createPerson() throws Exception {
		Person person = new Person();
		
		PersonResponse PersonResponse= new PersonResponse().setItem(person);
		
		String json = mapToJson(person);
		ResponseEntity<PersonResponse> PersonResponseEntity = new ResponseEntity<PersonResponse>(PersonResponse,HttpStatus.CREATED);
		when(personController.savePerson(person)).thenReturn(PersonResponseEntity);
		
	    RequestBuilder requestBuilder = MockMvcRequestBuilders
	            .post("/api/v1/Persons")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(json)
	            .contentType(MediaType.APPLICATION_JSON);

	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	    MockHttpServletResponse response = result.getResponse();

	    assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@Order(1)
	public void createPersonWithValidationError() throws Exception {
		Person person = new Person();
		
		PersonResponse PersonResponse= new PersonResponse().setItem(person);
		
		String json = mapToJson(person);
		ResponseEntity<PersonResponse> PersonResponseEntity = new ResponseEntity<PersonResponse>(PersonResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		when(personController.savePerson(person)).thenReturn(PersonResponseEntity);
		
	    RequestBuilder requestBuilder = MockMvcRequestBuilders
	            .post("/api/v1/Persons")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(json)
	            .contentType(MediaType.APPLICATION_JSON);

	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	    MockHttpServletResponse response = result.getResponse();

	    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	@Order(1)
	public void createPersonWithValidation() throws Exception {
		Person person = new Person();
		
		PersonResponse PersonResponse= new PersonResponse().setItem(person);
		
		String json = mapToJson(person);
		ResponseEntity<PersonResponse> PersonResponseEntity = new ResponseEntity<PersonResponse>(PersonResponse,HttpStatus.CREATED);
		when(personController.savePerson(person)).thenReturn(PersonResponseEntity);
		
	    RequestBuilder requestBuilder = MockMvcRequestBuilders
	            .post("/api/v1/person")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(json)
	            .contentType(MediaType.APPLICATION_JSON);

	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	    MockHttpServletResponse response = result.getResponse();

	    assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	@Order(2)
	public void getPersonByName() throws Exception {

		Person person = new Person();
		
		PersonResponse PersonResponse= new PersonResponse().setItem(person);
		ResponseEntity<PersonResponse> PersonResponseEntity = new ResponseEntity<PersonResponse>(PersonResponse,HttpStatus.OK);
		when(personController.getPersonDetails("test")).thenReturn(PersonResponseEntity);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/Amit")).andExpect(status().isOk());
				
	}

	@Test
	@Order(3)
	public void updatePerson() throws Exception {

		Person person = new Person();
		
		PersonResponse PersonResponse= new PersonResponse().setItem(person);
		ResponseEntity<PersonResponse> PersonResponseEntity = new ResponseEntity<PersonResponse>(PersonResponse,HttpStatus.OK);
		when(personController.getPersonDetails("test")).thenReturn(PersonResponseEntity);

		String json = mapToJson(person);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/person/test").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
		;
	}

	
}
