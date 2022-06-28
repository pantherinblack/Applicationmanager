/**
 * Loads the content of the page and does the logic behind the saving etc.
 * @author Kevin
 * @since 26.06.2022
 * @version 1.0
 */
document.addEventListener("DOMContentLoaded", () => {
    loadTypeList()
});

let uuid = null;

/**
 * loads the content of the table and manages who has access to what
 */
function loadTypeList() {
    uuid = getQueryParam("languageUuid");
    let role = getCookie("userRole");

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
            data.forEach(type => {
                let option = document.createElement("option");
                option.text = type.typeName;
                option.value = type.typeUuid;

                document.getElementById("typeUuid").add(option);
            })
        })
        .catch(function (error) {
            console.log(error);
        });


    document.getElementById("saveButton").hidden = true;
    document.getElementById("resetButton").hidden = true;
    document.getElementById("languageName").readonly = true;
    document.getElementById("languageShort").readonly = true;
    document.getElementById("languageRelDate").readonly = true;
    document.getElementById("typeUuid").readonly = true;

    if (role != null && role === "admin") {
        document.getElementById("saveButton").hidden = false;
        document.getElementById("resetButton").hidden = false;
        document.getElementById("languageName").readonly = false;
        document.getElementById("languageShort").readonly = false;
        document.getElementById("languageRelDate").readonly = false;
        document.getElementById("typeUuid").readonly = false;
    }

    $("#editForm").submit(saveLanguage)
    document.getElementById("cancelButton").addEventListener("click", cancel);

    if (uuid != null && uuid !== "") {

        fetch("./resource/language/readuuid?languageUuid=" + uuid)
            .then(function (response) {
                if (response.ok) {
                    return response;
                } else {
                    console.log(response);
                }
            })
            .then(response => response.json())
            .then(data => {
                showLanguage(data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

}

/**
 * saves the language to the database
 * @param form to be saved
 */
function saveLanguage(form) {
    form.preventDefault();

    if (uuid == null || uuid === "") {
        $
            .ajax({
                url: "./resource/language/create",
                dataType: "text",
                type: "POST",
                data: $("#editForm").serialize(),
            }).done(function () {
            window.location.href = "./listlanguages.html";
        }).fail(function (xhr, status, errorThrown) {
            console.log(xhr);
            console.log(status);
            console.log(errorThrown);
        });
    } else {
        document.getElementById("languageUuid").value = uuid;

        $
            .ajax({
                url: "./resource/language/update",
                dataType: "text",
                type: "PUT",
                data: $("#editForm").serialize(),
            }).done(function () {
            window.location.href = "./listlanguages.html";
        }).fail(function (xhr, status, errorThrown) {
            console.log(xhr.status);
            console.log(status);
            console.log(errorThrown);
        });
    }

}

/**
 * sets the values into the texfields when the pages is loaded
 * @param data
 */
function showLanguage(data) {
    document.getElementById("languageName").value = data.languageName;
    document.getElementById("languageShort").value = data.languageShort;
    document.getElementById("languageRelDate").value = data.languageReleaseDate;


}

/**
 * sends the user to the last page, when the cancle button ist klicked
 */
function cancel() {
    history.back();
}