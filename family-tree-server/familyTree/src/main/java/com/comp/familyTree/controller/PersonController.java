package com.comp.familyTree.controller;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.comp.familyTree.constants.Constants;
import com.comp.familyTree.entities.Person;
import com.comp.familyTree.entities.ResponseStatus;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;
import com.comp.familyTree.response.ErrorMessage;
import com.comp.familyTree.response.HTTPStatus;
import com.comp.familyTree.response.PersonResponse;
import com.comp.familyTree.service.PersonService;

/**
 * @author Amit Srivastava
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PersonService personService;

	Logger logger = LoggerFactory.getLogger(PersonController.class);

	/**
	 * This rest API is to get the list of all the PERSONs
	 * 
	 * @param personName
	 * @return Response with person details
	 */
	@GetMapping("/api/v1/person/{personName}")
	public ResponseEntity<PersonResponse> getPersonDetails(@PathVariable(value = "personName") String personName) {
		long startTime = System.currentTimeMillis();
		logger.info("START: getPersonDetails Service is called");
		PersonResponse PERSONResponse = new PersonResponse();
		try {
			logger.info("person name is :" + personName);
			Person personDetails = personService.getPersonDetails(personName);

			if (personDetails == null) {
				logger.error("person not found for the name : " + personName);
				PERSONResponse.setStatus(ResponseStatus.FAILED)
						.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
								.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONResponse);
			} else {
				logger.debug("got the person details : " + personDetails.toString());
				PERSONResponse.setStatus(ResponseStatus.SUCCESS).setItem(personDetails);
				long endTime = System.currentTimeMillis();
				logger.info("END: getPERSONDetails Service is completed in " + (endTime - startTime) + " ms");
				return ResponseEntity.status(HttpStatus.OK).body(PERSONResponse);

			}

		} catch (PersonNotFoundException e) {
			logger.error(e.getMessage());
			PERSONResponse.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
					.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONResponse);
		} catch (Exception e) {
			logger.error(e.getMessage());
			PERSONResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
					messageSource.getMessage(Constants.ERROR, new Object[] { personName }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(PERSONResponse);
		}
	}
	
	/**
	 * This rest API is to get the list of all the PERSONs
	 * 
	 * @param personName
	 * @return Response with person details
	 */
	@GetMapping("/api/v1/person")
	public ResponseEntity<List<Person>> getAllPersonDetails() {
		long startTime = System.currentTimeMillis();
		List<Person> persons= new ArrayList<Person>();
		logger.info("START: getAllPersonDetails Service is called");
		PersonResponse PERSONResponse = new PersonResponse();
		try {
			persons = personService.getAllPersonDetails();

			if (persons == null || persons.isEmpty()) {
				logger.error("No persons found" );
				PERSONResponse.setStatus(ResponseStatus.FAILED)
						.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
								.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { }, Locale.ENGLISH)));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(persons);
			} else {
				logger.debug("got the person details : " + persons.toString());
				
				long endTime = System.currentTimeMillis();
				logger.info("END: getPERSONDetails Service is completed in " + (endTime - startTime) + " ms");
				return ResponseEntity.status(HttpStatus.OK).body(persons);

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			PERSONResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
					messageSource.getMessage(Constants.ERROR, new Object[] { }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(persons);
		}
	}

	

	/**
	 * This rest API is to add a person
	 * 
	 * @param person
	 * @return {@link Person}
	 */
	@PostMapping("/api/v1/person")
	public ResponseEntity<PersonResponse> savePerson(@Valid @RequestBody Person person) {
		long startTime = System.currentTimeMillis();
		logger.info("START: savePERSON Service is called");
		PersonResponse PERSONResponse = new PersonResponse();
		Person savedPERSON = null;
		try {
			savedPERSON = personService.savePerson(person);

			if (savedPERSON != null) {
				ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
				builder.path(File.separator + savedPERSON.getPersonName().toString());
				URI location = builder.build().toUri();
				long endTime = System.currentTimeMillis();
				logger.info("END: savePERSON Service is completed in " + (endTime - startTime) + " ms");
				PERSONResponse.setStatus(ResponseStatus.SUCCESS).setItem(savedPERSON);
				return ResponseEntity.created(location).body(PERSONResponse);
			} else {
				PERSONResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
						messageSource.getMessage(Constants.PERSON_NOT_CREATED, new Object[] { person.getPersonName() },
								Locale.ENGLISH)));
				logger.error("Error when called savePERSON Service, updated PERSON is null");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(PERSONResponse);

			}

		} catch (FamilyTreeException e) {
			logger.error(e.getMessage());
			PERSONResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(), messageSource
					.getMessage(Constants.ERROR, new Object[] { person.getPersonName() }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	/**
	 * This rest is to update the person details
	 * 
	 * @param person
	 * @return {@link Person}
	 */
	@PutMapping("/api/v1/person/{personName}")
	public ResponseEntity<PersonResponse> updatePERSONDetails(@PathVariable(value = "personName") String personName,
			@Valid @RequestBody Person person) {
		long startTime = System.currentTimeMillis();
		logger.info("START: updatePERSONDetails Service is called");
		PersonResponse personResponse = new PersonResponse();
		Person savedPerson = null;
		try {
			savedPerson = personService.updatePerson(personName, person);
			if (savedPerson != null) {
				personResponse.setStatus(ResponseStatus.SUCCESS).setItem(savedPerson);
				long endTime = System.currentTimeMillis();
				logger.info("END: updatePERSONDetails Service is completed in " + (endTime - startTime) + " ms");
				return ResponseEntity.status(HttpStatus.OK).body(personResponse);
			} else {
				personResponse.addErrorMessage(
						new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(), messageSource.getMessage(
								Constants.PERSON_NOT_UPDATED, new Object[] { personName }, Locale.ENGLISH)));
				logger.error("Error when called updatePERSONDetails Service, updated PERSON is null");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(personResponse);

			}

		} catch (PersonNotFoundException e) {
			logger.error(e.getMessage());
			personResponse.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
					.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personResponse);
		} catch (Exception e) {
			logger.error("Error when updating the person details,Error Message :" + e.getMessage());
			personResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
					messageSource.getMessage(Constants.ERROR, new Object[] { personName }, Locale.ENGLISH)));

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}