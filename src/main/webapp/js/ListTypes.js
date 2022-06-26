document.addEventListener("DOMContentLoaded", () => {
    readType()
});

function readType() {
    fetch("./resource/type/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showTypes(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function showTypes(data) {
    let table = document.getElementById("types");

    table.innerHTML = "";
    data.forEach(type => {
        let row = table.insertRow(-1);

        row.insertCell(-1).innerHTML = type.typeName;
        row.insertCell(-1).innerHTML = type.typeDescription;

        let editButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.setAttribute("data-typeUuid", type.typeUuid);
        editButton.type = "button";
        editButton.addEventListener("click", editType);

        let viewButton = document.createElement("button");
        viewButton.innerHTML = "View";
        viewButton.setAttribute("data-typeUuid", type.typeUuid);
        viewButton.type = "button";
        viewButton.addEventListener("click", viewType)

        row.insertCell(-1).appendChild(viewButton);
        row.insertCell(-1).appendChild(editButton);
    })
}

function editType(event) {
    let typeUuid = event.target.getAttribute("data-typeUuid");
    window.location.href = "./edittype.html?typeUuid=" + typeUuid;

}

function viewType(event) {
    let typeUuid = event.target.getAttribute("data-typeUuid");
    window.location.href = "./viewtype.html?typeUuid=" + typeUuid;
}


function showPerson(data) {
    // get the input-element for the firstname
    let firstname = document.getElementById("firstname");
    // set the value of the input-element from the JSON-data
    firstname.value = data.firstname;

    // repeat for the rest of the attributes (and use a shorter notation)
    document.getElementById("lastname").value = data.lastname;
    document.getElementById("age").value = data.lastname;
    document.getElementById("hobbies").value = data.hobbies.join(", ");

}

function readPeople() {
    fetch("./people.json")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showPeople(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function showPeople(data) {
    // find the table-body element
    let tBody = document.getElementById("people");

    // clear existing rows/cells in the table-body
    tBody.innerHTML = "";

    // loop over all array elements
    data.forEach(person => {
        // insert a new row at the end of the table
        let row = tBody.insertRow(-1);

        // insert a cell at the end of the current row
        let cell = row.insertCell(-1);
        // set the content of the cell from the JSON-data
        cell.innerHTML = person.firstname;

        // repeat for the rest of the attributes/cells (and use a shorter notation)
        row.insertCell(-1).innerHTML = person.lastname;
        row.insertCell(-1).innerHTML = person.age;
        row.insertCell(-1).innerHTML = person.hobbies.join(", ");
    })

}