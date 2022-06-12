package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.annotation.ExistingUuid;
import ch.bzz.applicationmanager.annotation.Length;
import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Language;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
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
            @QueryParam("contains") String filter
    ) {
        List<Language> languages = DataHandler.readAllLanguages();
        if (filter != null && !filter.isEmpty()) {
            languages.removeIf(language -> !language.getLanguageName().toUpperCase().contains(filter.toUpperCase()));
        }
        return Response.status(200).entity(languages).build();
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
    public Response readLanguageByUuid(@ExistingUuid @QueryParam("languageUuid") String languageUuid) {
        Language language = DataHandler.readLanguageByUuid(languageUuid);
        if (language == null) return Response.status(400).entity(null).build();
        else return Response.status(200).entity(language).build();
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
    public Response readLanguageByName(@Size(min = 2, max = 50) @QueryParam("languageName") String languageName) {
        Language language = DataHandler.readLanguageByName(languageName);
        if (language == null) return Response.status(400).entity(null).build();
        else return Response.status(200).entity(language).build();
    }

    /**
     * creates Language in the DataHandler.
     *
     * @param languageName     of the language.
     * @param languageShort,   short name of the language.
     * @param languageRelDate, release-date of hte language.
     * @param languageType     of the language, uuid.
     * @return nothing as a response.
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    @Length
    public Response createLanguage(
            @FormParam("languageName") @Size(min = 2, max = 50) String languageName,
            @FormParam("languageShort") @Size(min = 1, max = 10) String languageShort,
            @FormParam("languageRelDate") @NotBlank @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}") String languageRelDate,
            @FormParam("typeUuid") @ExistingUuid String languageType
    ) {
        int httpStatus = 200;
        Language language = new Language();
        language.setLanguageName(languageName);
        language.setLanguageShort(languageShort);
        language.setLanguageType(languageType);
        language.setLanguageReleaseDate(LocalDate.parse(languageRelDate));
        language.setLanguageUuid(UUID.randomUUID().toString());
        DataHandler.insertLanguage(language);
        return Response.status(httpStatus).entity("").build();
    }

    /**
     * changes the content of a language.
     *
     * @param languageName  to be changed to.
     * @param languageShort to be changed to.
     * @param languageUuid  of the language to be changed.
     * @param languageDate  to be changed to.
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
            @FormParam("languageRelDate") @NotBlank @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}") String languageDate,
            @FormParam("typeUuid") @ExistingUuid String languageType
    ) {
        Language oldLanguage = DataHandler.readLanguageByUuid(languageUuid);
        oldLanguage.setLanguageName(languageName);
        oldLanguage.setLanguageShort(languageShort);
        oldLanguage.setLanguageReleaseDate(LocalDate.parse(languageDate));
        oldLanguage.setLanguageType(languageType);
        DataHandler.updateLanguage();

        return Response.status(200).entity("").build();
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
    public Response deleteLanguage(@ExistingUuid @QueryParam("languageUuid") String languageUuid) {
        DataHandler.deleteLanguage(languageUuid);
        return Response.status(200).entity("").build();
    }
}
