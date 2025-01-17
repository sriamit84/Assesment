package com.comp.familyTree.response;

import com.comp.familyTree.entities.Data;
import com.comp.familyTree.entities.Relation;
import com.comp.familyTree.entities.ResponseStatus;

public class RelationResponse {

	private String status;

	public String getStatus() {
		return status;
	}

	public Data<Relation> getData() {
		return data;
	}

	private Data<Relation> data;

	public RelationResponse() {
		data = new Data<Relation>();
	}

	public RelationResponse setStatus(ResponseStatus status) {
		this.status = status.getCode();
		return this;
	}

	public RelationResponse setItem(Relation relation) {
		this.data.setItem(relation);
		return this;
	}

	public RelationResponse addErrorMessage(ErrorMessage errorMessage) {
		this.data.addErrorMessage(errorMessage);
		return this;
	}

}
