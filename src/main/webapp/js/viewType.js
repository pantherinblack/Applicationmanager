document.addEventListener("DOMContentLoaded", () => {
    viewType()
});

function viewType() {
    let uuid = getQueryParam("typeUuid");

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