import react, { Component } from "react";
import { savePerson, getAllPersons } from "../../providers/familytree.service";
import { PersonListComponent } from "../persons/personlist.component";
class PersonComponent extends Component {
  constructor(props) {
    super(props);
    this.state = { name: "" };
  }

  clearAll = (e) => {
    this.setState({ name: "" });
  };

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

    console.log(this.data);

    savePerson(this.data)
      .then(
        (res) => {
          if (res.status == "success") {
            alert("Record created successfully");
            this.setState({ name: "" });
          } else {
            alert("Error when creating the person");
          }
        },
        (error) => alert("Error when creating the person")
      )
      .catch((error) => alert("Error when creating the person"));
  };

  showAllPersons = () => {
    getAllPersons().then((res) => {
      this.setState({ persons: res });
    });
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
              <div className="col-25"></div>
              <div className="col-75">
                <input type="submit" value="Add" />
                <input type="button" value="Clear" onClick={this.clearAll} />
                <input
                  type="button"
                  value="Show All"
                  onClick={this.showAllPersons}
                />
              </div>
            </div>
          </form>
          {/* {this.state.persons && this.state.persons.length ? (
            <PersonListComponent />
          ) : (
            ""
          )} */}
        </div>
      </>
    );
  }
}

export default PersonComponent;
