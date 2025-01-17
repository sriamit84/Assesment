package com.comp.familyTree.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.comp.familyTree.entities.ResponseStatus;
import com.comp.familyTree.response.ErrorMessage;
import com.comp.familyTree.response.HTTPStatus;
import com.comp.familyTree.response.PersonResponse;

@ControllerAdvice
@RestController
public class FamilyTreeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		PersonResponse productResponse = new PersonResponse();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			ErrorMessage errorDetails = new ErrorMessage(HTTPStatus.BAD_REQUEST.getCode(), error.getDefaultMessage());
			productResponse.addErrorMessage(errorDetails);
		});

		if (ex.getBindingResult().hasErrors()) {
			productResponse.setStatus(ResponseStatus.FAILED);
		}

		return new ResponseEntity<Object>(productResponse, HttpStatus.BAD_REQUEST);
	}

}