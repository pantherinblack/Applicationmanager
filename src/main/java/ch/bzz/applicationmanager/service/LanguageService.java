package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Language;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("language")
public class LanguageService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response langaugeList() {
        List<Language> languages = DataHandler.getInstance().readAllLanguages();
        return Response.status(200).entity(languages).build();
    }

    @GET
    @Path("readuuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLanguageByUuid(@QueryParam("uuid") String languageUuid) {
        if (languageUuid == null || languageUuid.isEmpty()) return Response.status(400).entity(null).build();
        Language language = DataHandler.getInstance().readLanguageByUuid(languageUuid);
        if (language==null) return Response.status(404).entity(null).build();
        else return Response.status(200).entity(language).build();
    }

    @GET
    @Path("readname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLanguageByName(@QueryParam("name") String languageName) {
        if (languageName == null || languageName.isEmpty()) return Response.status(400).entity(null).build();
        Language language = DataHandler.getInstance().readLanguageByName(languageName);
        if (language==null) return Response.status(404).entity(null).build();
        else return Response.status(200).entity(language).build();
    }
}
