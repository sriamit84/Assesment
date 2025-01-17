package com.comp.familyTree.service;

import java.util.List;

import javax.validation.Valid;

import com.comp.familyTree.entities.Relation;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;


public interface RelationsService {
	
	public Relation saveRelation(Relation relation) throws FamilyTreeException;
	
	public Relation getRelation(String personName) throws RuntimeException, Exception;

	public Relation updateRelation(String personName,  @Valid Relation relation) throws PersonNotFoundException, FamilyTreeException;

	public List<Relation> getAllRelations() throws FamilyTreeException;

}
