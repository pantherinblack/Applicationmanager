/**
 * manages the data of the languageview
 * @author Kevin
 * @since 26.06.2022
 * @version 1.0
 */
document.addEventListener("DOMContentLoaded", () => {
    viewLangauge()
});

/**
 * gets the data of hte language
 */
function viewLangauge() {
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

/**
 * shows a language
 * @param data of the language
 */
function showLanguage(data) {
    document.getElementById("languageName").value = data.languageName;
    document.getElementById("languageShort").value = data.languageShort;
    document.getElementById("languageReleaseDate").value = data.languageReleaseDate;
    document.getElementById("languageTypeName").value = data.languageTypeRef.typeName;
}