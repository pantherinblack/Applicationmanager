document.addEventListener("DOMContentLoaded", () => {
    loadTypeList()
});

let uuid = null;

function loadTypeList() {
    uuid = getQueryParam("typeUuid");

    $("#editForm").submit(saveType)
    document.getElementById("cancelButton").addEventListener("button", cancel);

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


function saveType(form) {
    form.preventDefault();
    const typeForm = document.getElementById("editForm");
    const formData = new FormData(typeForm);
    const data = new URLSearchParams(formData);

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

function cancel() {
    history.back();
}