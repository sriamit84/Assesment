package com.comp.familyTree.service;

import java.util.List;

import javax.validation.Valid;

import com.comp.familyTree.entities.Person;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;


public interface PersonService {
	
	public Person savePerson(Person person) throws FamilyTreeException;
	
	public Person getPersonDetails(String personName) throws RuntimeException, Exception;

	public Person updatePerson(String personName,  @Valid Person person) throws PersonNotFoundException, FamilyTreeException;

	public List<Person> getAllPersonDetails() throws FamilyTreeException;

}
