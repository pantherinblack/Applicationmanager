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
 * loads the content of the form
 */
function loadTypeList() {
    uuid = getQueryParam("typeUuid");
    let role = getCookie("userRole");


    document.getElementById("saveButton").hidden = true;
    document.getElementById("resetButton").hidden = true;
    document.getElementById("typeName").readOnly = true;
    document.getElementById("typeDesc").readOnly = true;

    if (role != null && role === "admin") {
        document.getElementById("saveButton").hidden = false;
        document.getElementById("resetButton").hidden = false;
        document.getElementById("typeName").readOnly = false;
        document.getElementById("typeDesc").readOnly = false;
    }

    $("#editForm").submit(saveType)
    document.getElementById("cancelButton").addEventListener("click", cancel);

    if (uuid != null && uuid !== "") {

        fetch("./resource/type/readuuid?typeUuid=" + uuid)
            .then(function (response) {
                if (response.ok) {
                    return response;
                } else {
                    console.log(response);
                }
            })
            .then(response => response.json())
            .then(data => {
                showType(data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

}

/**
 * saves the type to the database
 * @param form containing the data
 */
function saveType(form) {
    form.preventDefault();

    if (uuid == null || uuid === "") {
        $
            .ajax({
                url: "./resource/type/create",
                dataType: "text",
                type: "POST",
                data: $("#editForm").serialize(),
            }).done(function () {
            window.location.href = "./listtypes.html";
        }).fail(function (xhr, status, errorThrown) {
            console.log(xhr);
            console.log(status);
            console.log(errorThrown);
        });
    } else {
        document.getElementById("typeUuid").value = uuid;

        $
            .ajax({
                url: "./resource/type/update",
                dataType: "text",
                type: "PUT",
                data: $("#editForm").serialize(),
            }).done(function () {
            window.location.href = "./listtypes.html";
        }).fail(function (xhr, status, errorThrown) {
            console.log(xhr);
            console.log(status);
            console.log(errorThrown);
        });
    }

}

/**
 * Activated when the canlce button is pressed.
 * goes back to the last page visited.
 */
function cancel() {
    history.back();
}