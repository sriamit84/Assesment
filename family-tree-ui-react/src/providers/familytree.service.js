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
