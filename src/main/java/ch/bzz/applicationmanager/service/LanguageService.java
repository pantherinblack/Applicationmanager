package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Language;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    public Response readLanguageByUuid(@NotBlank @QueryParam("uuid") String languageUuid) {
        Language language = DataHandler.readLanguageByUuid(languageUuid);
        if (language == null) return Response.status(400).entity(null).build();
        else return Response.status(200).entity(language).build();
    }

    /**
     * gives back a specific language, first object with the requested name.
     *
     * @param languageName to search for.
     * @return project
     */
    @GET
    @Path("readname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLanguageByName(@Size(min = 2, max = 50) @QueryParam("name") String languageName) {
        Language language = DataHandler.readLanguageByName(languageName);
        if (language == null) return Response.status(400).entity(null).build();
        else return Response.status(200).entity(language).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createLanguage(
            @Valid @BeanParam Language language,
            @FormParam("typeuuid") String languageType
    ) {
        int httpStatus = 200;
        language.setLanguageType(languageType);
        language.setLanguageUuid(UUID.randomUUID().toString());
        DataHandler.insertLanguage(language);
        return Response.status(httpStatus).entity("").build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateLanguage(
            @FormParam("uuid") String languageUuid,
            @Valid @BeanParam Language language,
            @FormParam("typeuuid") String languageType
    ) {
        int httpStatus = 400;
        Language oldLanguage = DataHandler.readLanguageByUuid(languageUuid);
        if (oldLanguage != null) {
            oldLanguage.setLanguageName(language.getLanguageName());
            oldLanguage.setLanguageShort(language.getLanguageShort());
            oldLanguage.setLanguageReleaseDate(language.getLanguageReleaseDate());
            oldLanguage.setLanguageType(languageType);
            DataHandler.updateLanguage();
            httpStatus = 200;
        }

        return Response.status(httpStatus).entity("").build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLanguage(@QueryParam("languageUuid") String languageUuid) {
        int httpStatus = 410;
        if (DataHandler.deleteLanguage(languageUuid)) {
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity("").build();
    }
}
