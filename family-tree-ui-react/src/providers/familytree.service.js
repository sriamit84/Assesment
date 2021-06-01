/*
* This is the provider which has all the services for persons
*/

// This function saves the person details on backend
export function savePerson(person) {
  return new Promise((resolve, reject) => {
    fetch("http://localhost:8080/familyTree/api/v1/person/", {
      method: "POST",
      body: JSON.stringify(person),
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
    })
      .then((res) => res.json())
      .then((res) => {
        resolve(res);
      })
      .catch((error) => reject(error));
  });
}

// This function saves the person relations on backend
export function saveRelation(relation) {
  return new Promise((resolve, reject) => {
    fetch("http://localhost:8080/familyTree/api/v1/relations/", {
      method: "POST",
      body: JSON.stringify(relation),
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
    })
      .then((res) => res.json())
      .then((res) => {
        resolve(res);
      })
      .catch((error) => reject(error));
  });
}

// This function gets the persons and their relations from backend
export function getAllPersons(relation) {
  return new Promise((resolve, reject) => {
    fetch("http://localhost:8080/familyTree/api/v1/person", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((res) => {
        resolve(res);
      })
      .catch((error) => reject(error));
  });
}
