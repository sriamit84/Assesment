package com.comp.familyTree.controller;

import java.io.File;
import java.net.URI;
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
import com.comp.familyTree.entities.Relation;
import com.comp.familyTree.entities.ResponseStatus;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;
import com.comp.familyTree.model.FamilyTreeGraph;
import com.comp.familyTree.response.ErrorMessage;
import com.comp.familyTree.response.HTTPStatus;
import com.comp.familyTree.response.RelationResponse;
import com.comp.familyTree.service.PersonService;
import com.comp.familyTree.service.RelationsService;
import com.comp.familyTree.util.FamilyTreeUtil;

/**
 * @author Amit Srivastava
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RelationsController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RelationsService relationsService;

	@Autowired
	private PersonService personService;

	Logger logger = LoggerFactory.getLogger(RelationsController.class);

	/**
	 * This rest API is to get the list of all the relations
	 * 
	 * @param personName
	 * @return Response with Relation details
	 */
	@GetMapping("/api/v1/relations/{personName}")
	public ResponseEntity<RelationResponse> getRelations(@PathVariable(value = "personName") String personName) {
		long startTime = System.currentTimeMillis();
		logger.info("START: getRelations Service is called");
		RelationResponse relationResponse = new RelationResponse();
		try {
			logger.info("person name is :" + personName);
			Relation relationDetails = relationsService.getRelation(personName);

			if (relationDetails == null) {
				logger.error("person not found for the name : " + personName);
				relationResponse.setStatus(ResponseStatus.FAILED)
						.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
								.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(relationResponse);
			} else {
				logger.debug("got the person details : " + relationDetails.toString());
				relationResponse.setStatus(ResponseStatus.SUCCESS).setItem(relationDetails);
				long endTime = System.currentTimeMillis();
				logger.info("END: getRelationDetails Service is completed in " + (endTime - startTime) + " ms");
				return ResponseEntity.status(HttpStatus.OK).body(relationResponse);

			}

		} catch (PersonNotFoundException e) {
			logger.error(e.getMessage());
			relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
					.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(relationResponse);
		} catch (Exception e) {
			logger.error(e.getMessage());
			relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
					messageSource.getMessage(Constants.ERROR, new Object[] { personName }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(relationResponse);
		}
	}

	/**
	 * This rest API is to get the list of all the relations
	 * 
	 * @param personName
	 * @return Response with Person details
	 */
	@GetMapping("/api/v1/relations")
	public ResponseEntity<FamilyTreeGraph> getAllRelations() {
		long startTime = System.currentTimeMillis();
		logger.info("START: getPersonDetails Service is called");
		try {
			List<Relation> relationDetails = relationsService.getAllRelations();
			List<Person> persons = personService.getAllPersonDetails();

			FamilyTreeGraph familyTreeGraph = FamilyTreeUtil.transformResponse(relationDetails,persons);

			logger.debug("got the person details : " + relationDetails.toString());
			// RelationResponse.setStatus(ResponseStatus.SUCCESS).setItem(personDetails);
			long endTime = System.currentTimeMillis();
			logger.info("END: getRelationDetails Service is completed in " + (endTime - startTime) + " ms");
			return ResponseEntity.status(HttpStatus.OK).body(familyTreeGraph);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * This rest API is to add a Relation
	 * 
	 * @param relation
	 * @return {@link Person}
	 */
	@PostMapping("/api/v1/relations")
	public ResponseEntity<RelationResponse> saveRelation(@Valid @RequestBody Relation relation) {
		long startTime = System.currentTimeMillis();
		logger.info("START: saveRelation Service is called");
		RelationResponse relationResponse = new RelationResponse();
		Relation savedRelation = null;
		try {
			savedRelation = relationsService.saveRelation(relation);

			if (savedRelation != null) {
				ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
				builder.path(File.separator + savedRelation.getPersonName().toString());
				URI location = builder.build().toUri();
				long endTime = System.currentTimeMillis();
				logger.info("END: saveRelation Service is completed in " + (endTime - startTime) + " ms");
				relationResponse.setStatus(ResponseStatus.SUCCESS).setItem(savedRelation);
				return ResponseEntity.created(location).body(relationResponse);
			} else {
				relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
						messageSource.getMessage(Constants.RELATION_NOT_CREATED, new Object[] { relation.getPersonName() },
								Locale.ENGLISH)));
				logger.error("Error when called saveRelation Service, updated Relation is null");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(relationResponse);

			}

		} catch (FamilyTreeException e) {
			logger.error(e.getMessage());
			relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(), messageSource
					.getMessage(Constants.ERROR, new Object[] { relation.getPersonName() }, Locale.ENGLISH)));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	/**
	 * This rest is to update the Relation details
	 * 
	 * @param relation
	 * @return {@link Person}
	 */
	@PutMapping("/api/v1/relations/{personName}")
	public ResponseEntity<RelationResponse> updateRelation(@PathVariable(value = "personName") String personName,
			@Valid @RequestBody Relation relation) {
		long startTime = System.currentTimeMillis();
		logger.info("START: updateRelationDetails Service is called");
		RelationResponse relationResponse = new RelationResponse();
		Relation savedRelation = null;
		try {
			savedRelation = relationsService.updateRelation(personName, relation);
			if (savedRelation != null) {
				relationResponse.setStatus(ResponseStatus.SUCCESS).setItem(savedRelation);
				long endTime = System.currentTimeMillis();
				logger.info("END: updateRelation Service is completed in " + (endTime - startTime) + " ms");
				return ResponseEntity.status(HttpStatus.OK).body(relationResponse);
			} else {
				relationResponse.addErrorMessage(
						new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(), messageSource.getMessage(
								Constants.RELATION_NOT_UPDATED, new Object[] { personName }, Locale.ENGLISH)));
				logger.error("Error when called updateRelationDetails Service, updated Relation is null");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(relationResponse);

			}

		} catch (PersonNotFoundException e) {
			logger.error(e.getMessage());
			relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.NOT_FOUND.getCode(), messageSource
					.getMessage(Constants.PERSON_NOT_FOUND, new Object[] { personName }, Locale.ENGLISH)));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(relationResponse);
		} catch (Exception e) {
			logger.error("Error when updating the person details,Error Message :" + e.getMessage());
			relationResponse.addErrorMessage(new ErrorMessage(HTTPStatus.INTERNAL_SERVER_ERROR.getCode(),
					messageSource.getMessage(Constants.ERROR, new Object[] { personName }, Locale.ENGLISH)));

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}