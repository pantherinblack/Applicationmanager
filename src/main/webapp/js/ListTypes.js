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

        let deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Delete";
        deleteButton.setAttribute("data-typeUuid", type.typeUuid);
        deleteButton.type = "button";
        deleteButton.addEventListener("click", deleteType)

        row.insertCell(-1).appendChild(viewButton);
        row.insertCell(-1).appendChild(editButton);
        row.insertCell(-1).appendChild(deleteButton);
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

function deleteType(event) {

    let typeUuid = event.target.getAttribute("data-typeUuid");

    $
        .ajax({
            url: "./resource/type/delete?typeUuid=" + typeUuid,
            dataType: "text",
            type: "DELETE",
        }).done(function () {
        window.location.href = "./listtypes.html";
    }).fail(function (xhr, status, errorThrown) {
        console.log(xhr);
        console.log(status);
        console.log(errorThrown);
    });
}