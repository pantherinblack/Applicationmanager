/**
 * manages the data of the languageview
 * @author Kevin
 * @since 26.06.2022
 * @version 1.0
 */
document.addEventListener("DOMContentLoaded", () => {
    viewType()
});

/**
 * gets the data of the type
 */
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