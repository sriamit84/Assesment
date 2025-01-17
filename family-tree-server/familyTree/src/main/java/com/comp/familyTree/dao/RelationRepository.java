package com.comp.familyTree.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comp.familyTree.entities.Relation;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {

	Optional<Relation> findByPersonName(String personName);

	

}
