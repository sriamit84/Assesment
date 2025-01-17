package com.comp.familyTree.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comp.familyTree.dao.PersonRepository;
import com.comp.familyTree.dao.RelationRepository;
import com.comp.familyTree.entities.Person;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;
import com.comp.familyTree.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private RelationRepository relationRepository;

	@Override
	@Transactional
	public Person savePerson(Person person) throws FamilyTreeException {
		try {
			return personRepository.save(person);
		} catch (Exception e) {
			throw new FamilyTreeException("Error when saving the product with details :" + person.toString());
		}

	}

	@Override
	public Person getPersonDetails(String personName) throws FamilyTreeException {
		try {
			Optional<Person> productDetails = personRepository.findByPersonName(personName);
			if (productDetails.isPresent())
				return productDetails.get();
			else {
				throw new PersonNotFoundException("Person with name " + personName + " not found");
			}
		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching the person with name :" + personName);
		}
	}

	@Override
	@Transactional
	public Person updatePerson(String personName, @Valid Person person)
			throws PersonNotFoundException, FamilyTreeException {
		try {
			Optional<Person> personDetails = personRepository.findByPersonName(personName);
			if (personDetails.isPresent()) {
				Person savedPerson = personDetails.get();
				savedPerson.setPersonName(person.getPersonName());
				
				return personRepository.save(savedPerson);

			} else {
				
				return personRepository.save(person);

			}

		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching the person with name :" + personName);
		}
	}

	@Override
	public List<Person> getAllPersonDetails() throws FamilyTreeException {
		try {
			return personRepository.findAll();
		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching all person");
		}
	}

}
