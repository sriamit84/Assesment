import react, { Component } from "react";

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

    fetch("http://localhost:8080/familyTree/api/v1/person/", {
      method: "POST",
      body: JSON.stringify(this.data),
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
    })
      .then((res) => res.json())
      .then((res) => {
        if (res.status == "success") {
          alert("Record created successfully");
          this.setState({ name: "" });
        }
      })
      .catch((error) => alert("Error when creating the person"))
      .finally(() => this.setState({ name: "", pname: "", relation: "" }));
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
              </div>
            </div>
          </form>
        </div>
      </>
    );
  }
}

export default PersonComponent;
