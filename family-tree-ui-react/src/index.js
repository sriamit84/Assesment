import React, { useMemo, useState, useEffect, Component } from "react";
import ReactDOM from "react-dom";
import Tabs from "./tabs/tabs.component";
import "./styles.css";
import FamilyTree from "./graph.component";
import RelationComponent from "./nodes/relation.component";
import PersonComponent from "./nodes/person.component";

export default class App extends Component {
  render() {
    return (
      <div>
        <h1>Family Tree</h1>
        <Tabs>
          <div label="Person">
            <PersonComponent />
          </div>
          <div label="Relations">
            <RelationComponent />
          </div>
          <div label="Graph">
            <FamilyTree />
          </div>
        </Tabs>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById("root"));