/**
 * lists all languages
 * @author Kevin
 * @since 26.06.2022
 * @version 1.0
 */
document.addEventListener("DOMContentLoaded", () => {
    readLanguage()
});

/**
 * reads all languages
 */
function readLanguage() {

    let role = getCookie("userRole");
    document.getElementById("newLanguage").hidden = true;

    if (role != null && role === "admin") {
        document.getElementById("newLanguage").hidden = false;
    }

    fetch("./resource/language/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showLanguages(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows a language
 * @param data of the langauge
 */
function showLanguages(data) {
    let table = document.getElementById("types");
    let role = getCookie("userRole");

    data.forEach(language => {
        let row = table.insertRow(-1);

        row.insertCell(-1).innerHTML = language.languageName;
        row.insertCell(-1).innerHTML = language.languageShort;
        row.insertCell(-1).innerHTML = language.languageReleaseDate;
        row.insertCell(-1).innerHTML = language.languageTypeRef.typeName;

        let editButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.setAttribute("data-languageUuid", language.languageUuid);
        editButton.type = "button";
        editButton.addEventListener("click", editLanguage);

        let viewButton = document.createElement("button");
        viewButton.innerHTML = "View";
        viewButton.setAttribute("data-languageUuid", language.languageUuid);
        viewButton.type = "button";
        viewButton.addEventListener("click", viewLanguage)

        let deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Delete";
        deleteButton.setAttribute("data-languageUuid", language.languageUuid);
        deleteButton.type = "button";
        deleteButton.addEventListener("click", deleteLanguage)

        row.insertCell(-1).appendChild(viewButton);
        row.insertCell(-1).appendChild(editButton);
        row.insertCell(-1).appendChild(deleteButton);

        viewButton.hidden = true;
        editButton.hidden = true;
        deleteButton.hidden = true;

        if (role != null && role !== "guest") {
            viewButton.hidden = false;
            if (role === "admin") {
                editButton.hidden = false;
                deleteButton.hidden = false;
            }

        }

    })
}

/**
 * sends the user to the edit page
 * @param event
 */
function editLanguage(event) {
    let listUuid = event.target.getAttribute("data-languageUuid");
    window.location.href = "./editlanguage.html?languageUuid=" + listUuid;

}

/**
 * sends the user to the view page
 * @param event
 */
function viewLanguage(event) {
    let listUuid = event.target.getAttribute("data-languageUuid");
    window.location.href = "./viewlanguage.html?languageUuid=" + listUuid;
}

/**
 * deletes a language
 * @param event
 */
function deleteLanguage(event) {

    let listUuid = event.target.getAttribute("data-languageUuid");

    $
        .ajax({
            url: "./resource/language/delete?languageUuid=" + listUuid,
            dataType: "text",
            type: "DELETE",
        }).done(function () {
        window.location.href = "./listlanguages.html";
    }).fail(function (xhr, status, errorThrown) {
        console.log(xhr);
        console.log(status);
        console.log(errorThrown);
    });
}