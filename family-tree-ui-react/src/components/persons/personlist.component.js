import React, { Component } from "react";
import PersonComponent from "./person.component";

function PersonListComponent(persons) {
  return persons.forEach((person) => {
    <ul>
      <PersonComponent person={person} />
    </ul>;
  });
}

export default PersonListComponent;
