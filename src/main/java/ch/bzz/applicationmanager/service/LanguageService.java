package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.annotation.ExistingUuid;
import ch.bzz.applicationmanager.annotation.Length;
import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.data.UserData;
import ch.bzz.applicationmanager.module.Language;
import ch.bzz.applicationmanager.uil.AESEncrypt;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * Service for the language model, able to give back a un/filtered list or to search for the first type with
 * the uuid or to search for the first object with the requested name.
 *
 * @author Kevin Stupar
 * @version 1.8
 * @since 24.05.2022
 */
@Path("language")
public class LanguageService {

    /**
     * gives back a list of language objects, can be filtered.
     *
     * @param filter to be searched for (not case-sensitive).
     * @return list of languages.
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response languageList(
            @CookieParam("complete") String complete,
            @QueryParam("contains") String filter
    ) {
        List<Language> languages = null;
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            languages = DataHandler.readAllLanguages();
            if (filter != null && !filter.isEmpty()) {
                languages.removeIf(language -> !language.getLanguageName().toUpperCase().contains(filter.toUpperCase()));
            }
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity(languages).build();
    }

    /**
     * gives back a specific language, first object with the requested uuid.
     *
     * @param languageUuid to search for.
     * @return project
     */
    @GET
    @Path("readuuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLanguageByUuid(
            @CookieParam("complete") String complete,
            @ExistingUuid @QueryParam("languageUuid") String languageUuid
    ) {
        Language language = null;
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            language = DataHandler.readLanguageByUuid(languageUuid);
            httpStatus = 200;
            if (language == null) httpStatus = 400;
        }
        return Response.status(httpStatus).entity(language).build();
    }

    /**
     * gives back a specific language, first object with the requested name.
     *
     * @param languageName to search for.
     * @return project.
     */
    @GET
    @Path("readname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLanguageByName(
            @CookieParam("complete") String complete,
            @Size(min = 2, max = 50) @QueryParam("languageName") String languageName
    ) {
        Language language = null;
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            language = DataHandler.readLanguageByName(languageName);
            httpStatus = 200;
            if (language == null) httpStatus = 400;

        }
        return Response.status(httpStatus).entity(language).build();
    }

    /**
     * creates Language in the DataHandler.
     *
     * @param languageName   of the language.
     * @param languageShort, short name of the language.
     * @param language,      release-date of hte language.
     * @param languageType   of the language, uuid.
     * @return nothing as a response.
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    @Length
    public Response createLanguage(
            @FormParam("languageName") @Size(min = 2, max = 50) String languageName,
            @FormParam("languageShort") @Size(min = 1, max = 10) String languageShort,
            @BeanParam @Valid Language language,
            @FormParam("typeUuid") @ExistingUuid String languageType,
            @CookieParam("complete") String complete
    ) {
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) {
            language.setLanguageName(languageName);
            language.setLanguageShort(languageShort);
            language.setLanguageType(languageType);
            language.setLanguageUuid(UUID.randomUUID().toString());
            httpStatus = 400;
            if (!DataHandler.isExistingLanguage(language)) {
                DataHandler.insertLanguage(language);
                httpStatus = 200;
            }
        }
        return Response.status(httpStatus).entity("").build();
    }

    /**
     * changes the content of a language.
     *
     * @param languageName  to be changed to.
     * @param languageShort to be changed to.
     * @param languageUuid  of the language to be changed.
     * @param language      to be changed to.
     * @param languageType  to be changed to.
     * @return nothing as a response.
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    @Length
    public Response updateLanguage(
            @FormParam("languageName") @Size(min = 2, max = 50) String languageName,
            @FormParam("languageShort") @Size(min = 1, max = 10) String languageShort,
            @FormParam("languageUuid") @ExistingUuid String languageUuid,
            @BeanParam @Valid Language language,
            @FormParam("typeUuid") @ExistingUuid String languageType,
            @CookieParam("complete") String complete
    ) {
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) {
            httpStatus = 400;
            language.setLanguageName(languageName);
            language.setLanguageShort(languageShort);
            language.setLanguageType(languageType);
            if (!DataHandler.isExistingLanguage(language)) {
                Language oldLanguage = DataHandler.readLanguageByUuid(languageUuid);
                oldLanguage.setLanguageName(languageName);
                oldLanguage.setLanguageShort(languageShort);
                oldLanguage.setLanguageReleaseDate(language.getLanguageReleaseDate());
                oldLanguage.setLanguageType(languageType);
                DataHandler.updateLanguage();
                httpStatus = 200;
            }
        }

        return Response.status(httpStatus).entity("").build();
    }

    /**
     * deletes a language.
     *
     * @param languageUuid of hte language to be deleted
     * @return nothing as a response.
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLanguage(
            @CookieParam("complete") String complete,
            @ExistingUuid @QueryParam("languageUuid") String languageUuid
    ) {
        int httpStatus = 403;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) {
            DataHandler.deleteLanguage(languageUuid);
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity("").build();
    }
}
