package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.UserData;
import ch.bzz.applicationmanager.module.User;
import ch.bzz.applicationmanager.uil.AESEncrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * manages all logins and logouts
 *
 * @author Kevin
 * @version 1.0
 * @since 26.06.2022
 */
@Path("user")
public class UserService {

    /**
     * logs the user in, if login is correct
     *
     * @param username of the user
     * @param password of the user
     * @param code     entered by the user
     * @param original code
     * @return response
     */
    @Path("login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("code") String code,
            @FormParam("original") String original
    ) {
        int httpStatus = 404;
        String text = "";
        User user = UserData.findUser(username, password);
        if (user != null && user.getUserRole() != null &&
                !user.getUserRole().equals("guest")
        ) {
            httpStatus = 200;
            text = user.getUserUUID() + "\n" + user.getUserName() + "\n" + user.getPassword() + "\n" + user.getUserRole();
        }

        NewCookie userCookie = null;
        NewCookie roleCookie = null;

        if (original != null && !original.isEmpty() && code.equals(original)) {
            userCookie = new NewCookie(
                    "complete",
                    AESEncrypt.encrypt(text),
                    "/",
                    "",
                    "Complete User",
                    600,
                    false
            );


            roleCookie = new NewCookie(
                    "userRole",
                    user.getUserRole(),
                    "/",
                    "",
                    "Login-Cookie",
                    600,
                    false
            );
            return Response.status(httpStatus).entity("").cookie(roleCookie).cookie(userCookie).build();
        } else {
            original = user.getUserMail() + "\n" + (((int) (Math.random() * 900000)) + 100000);

            return Response.status(httpStatus).entity(original).build();
        }
    }

    /**
     * logs a user out
     *
     * @return response
     */
    @Path("logout")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {

        NewCookie newCookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                1,
                false
        );

        NewCookie userCookie = new NewCookie(
                "complete",
                AESEncrypt.encrypt("guest"),
                "/",
                "",
                "Complete User",
                1,
                false
        );

        return Response.status(200).entity("").cookie(newCookie).cookie(userCookie).build();
    }

}
