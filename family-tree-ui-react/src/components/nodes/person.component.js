/*
* This component is used to render the Person with attributes like name and on click of show all it will show the detail of 
* the persons related with this person.
*/

import react, { Component } from "react";
import { savePerson, getAllPersons } from "../../providers/familytree.service";
import { PersonListComponent } from "../persons/personlist.component";
class PersonComponent extends Component {
  
  // Initialized person with state
  constructor(props) {
    super(props);
    this.state = { name: "" };
  }

  // clear the person from state
  clearAll = (e) => {
    this.setState({ name: "" });
  };

  // set the value of person name and value as key value pair in state
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
    // save person method is called which will save the person details in backend, on success we will show the alert on home page
    // that person creation is successful
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
  
  // Function to show all the persons stored in the state
  showAllPersons = () => {
    getAllPersons().then((res) => {
      this.setState({ persons: res });
    });
  };

  // This method will render the text box to enter person details and save, one new node will be created for this person on the backend
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
          
        </div>
      </>
    );
  }
}

export default PersonComponent;
