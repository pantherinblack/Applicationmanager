/**
 * manages all logins/logouts
 * @author Kevin
 * @since 26.06.2022
 * @version 1.0
 */
let code = null;

$(document).ready(function () {
    $("#loginForm").submit(sendLogin);
    $("#logout").click(sendLogout);
});

/**
 * sends the login
 * @param form
 */
function sendLogin(form) {
    form.preventDefault();
    document.getElementById("original").value = code;
    $
        .ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize(),
        }).done(function (response) {
        if (response !== "") {
            code = response.split("\n")[1];
            document.getElementById("code").value = response.split("\n")[1];
            document.getElementById("coderow").hidden = false;
            Email.send({
                Host: "smtp.gmail.com",
                Username: "applicationmanagercode@gmail.com",
                Password: "appTest01",
                To: response.split("\n")[0],
                From: "applicationmanagercode@gmail.com",
                Subject: "Login code",
                Body: "Code: " + response.split("\n")[1],
            }).then(
                $("#message").text("Mail versendet")
            );
        } else {
            window.location.href = "./listtypes.html";
        }
    }).fail(function (xhr, status, errorThrown) {
        if (xhr.status == 404) {
            $("#message").text("Benutzername/Passwort unbekannt")
        } else {
            $("#message").text("Es ist ein Fehler aufgetreten")
        }

    })
}

/**
 * sends the logout
 */
function sendLogout() {
    $.ajax({
        url: "./resource/user/logout",
        dataType: "text",
        type: "DELETE"
    }).done(function () {
        window.location.href = "./login.html"
    }).fail(function (xhr, status, errorThrown) {
        console.log(xhr);
        console.log(status);
        console.log(errorThrown);
    })
}