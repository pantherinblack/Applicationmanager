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

    if (uuid == null || uuid === "") {
        fetch("./resource/type/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: $("#editForm").serialize()
        }).then(function (response) {
            if (!response.ok) {
                console.log(response)
            } else {
                return response;
            }
        }).then().catch(function (error) {
            console.log(error)
        })
    } else {
        document.getElementById("uuid").value = uuid;
        fetch("./resource/type/update", {
            method: "PUT",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: $("#editForm").serialize()
        }).then(function (response) {
            if (!response.ok) {
                console.log($("#editForm").serialize());
                console.log(response);
            } else {
                return response;
            }
        }).then().catch(function (error) {
            console.log(error)
        })
    }

}

function cancel() {
    history.back();
}