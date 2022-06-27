document.addEventListener("DOMContentLoaded", () => {
    loadTypeList()
});

let uuid = null;

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

    if (role != null && role === "admin") {
        document.getElementById("saveButton").hidden = false;
        document.getElementById("resetButton").hidden = false;
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


function showLanguage(data) {
    document.getElementById("languageName").value = data.languageName;
    document.getElementById("languageShort").value = data.languageShort;
    document.getElementById("languageRelDate").value = data.languageReleaseDate;


}

function cancel() {
    history.back();
}