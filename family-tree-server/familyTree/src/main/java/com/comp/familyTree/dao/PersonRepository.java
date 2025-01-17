package com.comp.familyTree.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comp.familyTree.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	
	@Query("SELECT p FROM Person p WHERE p.personName = :name")
	Optional<Person> findByPersonName(
	  @Param("name") String name);

}
