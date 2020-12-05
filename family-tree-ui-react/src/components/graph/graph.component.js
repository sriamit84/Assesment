import react, { Component } from "react";
import { Graph } from "react-d3-graph";
export default class FamilyTree extends Component {
  constructor(props) {
    super(props);
    this.state = { nodes: [], links: [] };
  }

  componentDidMount() {
    fetch("http://localhost:8080/familyTree/api/v1/relations/", {
      method: "GET",
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        this.setState({ data: res });
      })
      .catch((error) => {
        console.log("Error when fetching the relations");
        // this.setState({ error: "Error when fetching the relations" });
      });
  }

  myConfig = {
    automaticRearrangeAfterDropNode: true,
    collapsible: false,
    directed: true,
    focusAnimationDuration: 0.75,
    focusZoom: 1,
    height: 400,
    highlightDegree: 1,
    highlightOpacity: 1,
    linkHighlightBehavior: true,
    maxZoom: 8,
    minZoom: 0.1,
    nodeHighlightBehavior: true,
    panAndZoom: false,
    staticGraph: false,
    staticGraphWithDragAndDrop: false,
    width: 1000,
    d3: {
      alphaTarget: 0.05,
      gravity: -100,
      linkLength: 200,
      linkStrength: 1,
      disableLinkForce: false,
    },
    node: {
      color: "orange",
      fontColor: "black",
      fontSize: 12,
      fontWeight: "normal",
      highlightColor: "SAME",
      highlightFontSize: 8,
      highlightFontWeight: "normal",
      highlightStrokeColor: "SAME",
      highlightStrokeWidth: "SAME",
      labelProperty: "id",
      mouseCursor: "pointer",
      opacity: 1,
      renderLabel: true,
      size: 1000,
      strokeColor: "black",
      strokeWidth: 1.5,
      svg: "",
      symbolType: "circle",
    },
    link: {
      color: "#d3d3d3",
      fontColor: "black",
      fontSize: 10,
      fontWeight: "normal",
      highlightColor: "SAME",
      highlightFontSize: 8,
      highlightFontWeight: "normal",
      labelProperty: "label",
      mouseCursor: "pointer",
      opacity: 1,
      renderLabel: true,
      semanticStrokeWidth: false,
      strokeWidth: 1.5,
      markerHeight: 6,
      markerWidth: 6,
    },
  };

  onNodePositionChange = function (nodeId, x, y) {
    window.alert(
      `Node ${nodeId} is moved to new position. New position is x= ${x} y= ${y}`
    );
  };
  render() {
    return this.state.data &&
      this.state.data.nodes &&
      this.state.data.nodes.length ? (
      <Graph
        id="graph-id" // id is mandatory, if no id is defined rd3g will throw an error
        data={this.state.data}
        config={this.myConfig}
        onClickNode={this.onClickNode}
        onDoubleClickNode={this.onDoubleClickNode}
        onRightClickNode={this.onRightClickNode}
        onClickGraph={this.onClickGraph}
        onClickLink={this.onClickLink}
        onRightClickLink={this.onRightClickLink}
        onMouseOverNode={this.onMouseOverNode}
        onMouseOutNode={this.onMouseOutNode}
        onMouseOverLink={this.onMouseOverLink}
        onMouseOutLink={this.onMouseOutLink}
        onNodePositionChange={this.onNodePositionChange}
      />
    ) : (
      "There is no data present as of now"
    );
    // <h2>My name is Amit</h2>
  }
}
