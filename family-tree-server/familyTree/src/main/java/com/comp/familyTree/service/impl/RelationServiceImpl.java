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
import com.comp.familyTree.entities.Relation;
import com.comp.familyTree.error.FamilyTreeException;
import com.comp.familyTree.error.PersonNotFoundException;
import com.comp.familyTree.service.PersonService;
import com.comp.familyTree.service.RelationsService;

@Service
public class RelationServiceImpl implements RelationsService {

	@Autowired
	private RelationRepository relationRepository;

	@Override
	@Transactional
	public Relation saveRelation(Relation relation) throws FamilyTreeException {
		try {
			return relationRepository.save(relation);
		} catch (Exception e) {
			throw new FamilyTreeException("Error when saving the product with details :" + relation.toString());
		}

	}

	@Override
	public Relation getRelation(String personName) throws FamilyTreeException {
		try {
			Optional<Relation> relationDetails = relationRepository.findByPersonName(personName);
			if (relationDetails.isPresent())
				return relationDetails.get();
			else {
				throw new PersonNotFoundException("Person with name " + personName + " not found");
			}
		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching the person with name :" + personName);
		}
	}

	@Override
	@Transactional
	public Relation updateRelation(String personName, @Valid Relation relation)
			throws PersonNotFoundException, FamilyTreeException {
		try {
			Optional<Relation> relations = relationRepository.findByPersonName(personName);
			if (relations.isPresent()) {
				Relation savedRelation = relations.get();
				savedRelation.setPersonName(relation.getPersonName());
				savedRelation.setParentName(relation.getParentName());
				savedRelation.setRelation(relation.getRelation());

				return relationRepository.save(savedRelation);

			} else {

				return relationRepository.save(relation);

			}

		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching the person with name :" + personName);
		}
	}

	@Override
	public List<Relation> getAllRelations() throws FamilyTreeException {
		try {
			return relationRepository.findAll();
		} catch (Exception e) {
			throw new FamilyTreeException("Error when fetching all person");
		}
	}

}
