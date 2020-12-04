package com.comp.familyTree.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FamilyTreeException extends Exception {

	private static final long serialVersionUID = 1L;

	public FamilyTreeException() {
		super();
	}

	public FamilyTreeException(String message) {
		super(message);
	}

	public FamilyTreeException(String message, Throwable cause) {
		super(message, cause);
	}

}
