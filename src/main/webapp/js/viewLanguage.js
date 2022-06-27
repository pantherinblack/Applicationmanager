document.addEventListener("DOMContentLoaded", () => {
    viewType()
});

function viewType() {
    let uuid = getQueryParam("languageUuid");

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

function showLanguage(data) {
    document.getElementById("languageName").value = data.languageName;
    document.getElementById("languageShort").value = data.languageShort;
    document.getElementById("languageReleaseDate").value = data.languageReleaseDate;
    document.getElementById("languageTypeName").value = data.languageTypeRef.typeName;
}