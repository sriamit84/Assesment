package com.comp.familyTree.response;

import com.comp.familyTree.entities.Data;
import com.comp.familyTree.entities.Person;
import com.comp.familyTree.entities.ResponseStatus;

public class PersonResponse {

	private String status;

	public String getStatus() {
		return status;
	}

	public Data<Person> getData() {
		return data;
	}

	private Data<Person> data;

	public PersonResponse() {
		data = new Data<Person>();
	}

	public PersonResponse setStatus(ResponseStatus status) {
		this.status = status.getCode();
		return this;
	}

	public PersonResponse setItem(Person product) {
		this.data.setItem(product);
		return this;
	}

	public PersonResponse addErrorMessage(ErrorMessage errorMessage) {
		this.data.addErrorMessage(errorMessage);
		return this;
	}

}
