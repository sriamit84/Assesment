package com.comp.familyTree.util;

import java.util.List;

import com.comp.familyTree.entities.Person;
import com.comp.familyTree.entities.Relation;
import com.comp.familyTree.model.FamilyTreeGraph;

public class FamilyTreeUtil {

	public static FamilyTreeGraph transformResponse(List<Relation> relations, List<Person> persons) {

		FamilyTreeGraph familyTreeGraph = new FamilyTreeGraph();
		for (Relation r : relations) {

			if (r.getRelation() != null && !r.getParentName().equals("")) {
				familyTreeGraph.addLink(r.getPersonName(), r.getParentName(), r.getRelation());
			}

		}

		for (Person p : persons) {
			familyTreeGraph.addNode(p.getPersonName());
		}
		return familyTreeGraph;

	}

}
