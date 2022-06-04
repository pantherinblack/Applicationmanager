package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Language;
import ch.bzz.applicationmanager.module.Type;

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
    public Response languageList(@QueryParam("contains") String filter) {
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
    public Response readLanguageByUuid(@QueryParam("uuid") String languageUuid) {
        if (languageUuid == null || languageUuid.isEmpty()) return Response.status(400).entity(null).build();
        Language language = DataHandler.readLanguageByUuid(languageUuid);
        if (language == null) return Response.status(404).entity(null).build();
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
    public Response readLanguageByName(@QueryParam("name") String languageName) {
        if (languageName == null || languageName.isEmpty()) return Response.status(400).entity(null).build();
        Language language = DataHandler.readLanguageByName(languageName);
        if (language == null) return Response.status(404).entity(null).build();
        else return Response.status(200).entity(language).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createLanguage(
            @FormParam("name") String languageName,
            @FormParam("relDate") String languageReleaseDate,
            @FormParam("short") String languageShort,
            @FormParam("type") String languageType
    ) {
        int httpStatus = 200;
        if (languageName != null && !languageName.isEmpty() && languageReleaseDate != null && !languageReleaseDate.isEmpty() && languageShort != null && !languageShort.isEmpty() && languageType != null && !languageType.isEmpty()) {
            Language language = new Language();
            language.setLanguageUuid(UUID.randomUUID().toString());
            language.setLanguageName(languageName);
            language.setLanguageReleaseDate(LocalDate.parse(languageReleaseDate));
            language.setLanguageShort(languageShort);
            language.setLanguageType(languageType);
            DataHandler.insertLanguage(language);
        } else {
            httpStatus = 400;
        }
        return Response.status(httpStatus).entity("").build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateType(
            @FormParam("typeUuid") String typeUuid,
            @FormParam("name") String name,
            @FormParam("desc") String description
    ) {
        int httpStatus = 404;
        if (typeUuid != null && typeUuid.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            Type type = DataHandler.readTypesByUuid(typeUuid);
            if (type != null) {
                type.setTypeName(name);
                type.setTypeDescription(description);
                DataHandler.updateType();
                httpStatus = 200;
            }
        }

        return Response.status(httpStatus).entity("").build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteType(@FormParam("typeUuid") String typeUuid) {
        int httpStatus = 404;
        if (typeUuid != null && typeUuid.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            if (DataHandler.deleteType(typeUuid)) {
                httpStatus = 200;
            }
        }
        return Response.status(httpStatus).entity("").build();
    }
}
