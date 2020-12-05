import react, { Component } from "react";
import { saveRelation } from "../../providers/familytree.service";
const inputParsers = {
  date(input) {
    const [month, day, year] = input.split("/");
    return `${year}-${month}-${day}`;
  },
  uppercase(input) {
    return input.toUpperCase();
  },
  number(input) {
    return parseFloat(input);
  },
};
class RelationComponent extends Component {
  constructor(props) {
    super(props);
    this.state = { name: "", pname: "", relation: "" };
  }

  clearAll = (e) => {
    this.setState({ name: "", pname: "", relation: "" });
  };

  componentDidMount() {}

  handleInputChange = (event) => {
    const value = event.target.value;
    const name = event.target.name;
    console.log(name + " " + value);
    this.setState({
      [name]: value,
    });
  };
  handleSubmit = (event) => {
    event.preventDefault();
    this.data = {};
    this.data.personName = this.state.name;
    this.data.parentName = this.state.pname;
    this.data.relation = this.state.relation;

    console.log(this.data);

    saveRelation(this.data)
      .then((res) => {
        if (res.status == "success") {
          alert("Record created successfully");
          this.setState({ name: "", pname: "", relation: "" });
        } else {
          alert("Record could not be created");
        }
      })
      .catch((error) => alert("Error when creating the relations"));
  };

  render() {
    return (
      <>
        <h2>Add/Edit Person</h2>

        <div className="container">
          <form onSubmit={this.handleSubmit}>
            <div className="row">
              <div className="col-25">
                <label for="name">Name</label>
              </div>
              <div className="col-75">
                <input
                  type="text"
                  id="name"
                  name="name"
                  placeholder="Enter Name"
                  onChange={this.handleInputChange}
                  value={this.state.name}
                  required
                />
              </div>
            </div>
            <div className="row">
              <div className="col-25">
                <label for="pname">Relative's Name</label>
              </div>
              <div className="col-75">
                <input
                  type="text"
                  id="pname"
                  name="pname"
                  placeholder="Enter Relative's Name"
                  onChange={this.handleInputChange}
                  value={this.state.pname}
                  required
                />
              </div>
            </div>
            <div className="row">
              <div className="col-25">
                <label for="relation">Relation</label>
              </div>
              <div className="col-75">
                <input
                  type="text"
                  id="relation"
                  name="relation"
                  placeholder="Enter Relation"
                  onChange={this.handleInputChange}
                  value={this.state.relation}
                  required
                />
              </div>
            </div>

            <div className="row">
              <div className="col-25"></div>
              <div className="col-75">
                <input type="submit" value="Add" />
                <input type="button" value="Clear" onClick={this.clearAll} />
              </div>
            </div>
          </form>
        </div>
      </>
    );
  }
}

export default RelationComponent;
