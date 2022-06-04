package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * Service for the type model, able to give back a un/filtered list or to search for the first project with
 * the uuid or to search for the first object with the requested name.
 *
 * @author Kevin Stupar
 * @version 1.6
 * @since 24.05.2022
 */
@Path("project")
public class ProjectService {

    /**
     * gives back a list of project objects, can be filtered.
     * @param filter to be searched for (not case-sensitive).
     * @return list of projects.
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProjects(@QueryParam("contains") String filter) {
        List<Project> projects = DataHandler.readAllProjects();
        if (filter != null && !filter.isEmpty()) {
            projects.removeIf(project -> !project.getProjectName().toUpperCase().contains(filter.toUpperCase()));
        }
        return Response.status(200).entity(projects).build();
    }

    /**
     * gives back a specific project, first object with the requested uuid.
     * @param projectUuid to search for.
     * @return project
     */
    @Path("readuuid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByUuid(@QueryParam("uuid") String projectUuid) {
        if (projectUuid == null || projectUuid.isEmpty()) return Response.status(400).entity(null).build();
        Project project = DataHandler.readProjectByUuid(projectUuid);
        if (project == null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(project).build();
    }

    /**
     * gives back a specific project, first object with the requested name.
     * @param projectName to search for.
     * @return project
     */
    @Path("readname")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByName(@QueryParam("name") String projectName) {
        if (projectName == null || projectName.isEmpty()) return Response.status(400).entity(null).build();
        Project project = DataHandler.readProjectByName(projectName);
        if (project == null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(project).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createLanguage(
            @FormParam("name") String projectName,
            @FormParam("version") String projectVersion,
            @FormParam("author") String projectAuthor
    ) {
        int httpStatus = 200;
        if (projectName != null && !projectName.isEmpty() && projectVersion != null && !projectVersion.isEmpty() && projectAuthor != null && !projectAuthor.isEmpty()) {
            Project project = new Project();
            project.setProjectUuid(UUID.randomUUID().toString());
            project.setProjectName(projectName);
            project.setProjectVersion(projectVersion);
            project.setProjectAuthor(projectAuthor);
            DataHandler.insertProject(project);
        } else {
            httpStatus = 400;
        }
        return Response.status(httpStatus).entity("").build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateType(
            @FormParam("uuid") String projectUuid,
            @FormParam("name") String projectName,
            @FormParam("version") String projectVersion,
            @FormParam("author") String projectAuthor
    ) {
        int httpStatus = 404;
        if (projectUuid != null && projectUuid.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            Project project = DataHandler.readProjectByUuid(projectUuid);
            if (project != null) {
                project.setProjectName(projectName);
                project.setProjectVersion(projectVersion);
                project.setProjectVersion(projectVersion);
                project.setProjectAuthor(projectAuthor);
                DataHandler.updateProject();
                httpStatus = 200;
            }
        }

        return Response.status(httpStatus).entity("").build();
    }

    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLanguage(
            @FormParam("uuid") String projectUuid,
            @FormParam("language") String language
    ) {
        DataHandler.readProjectByUuid(projectUuid).addLanguage(projectUuid);
        DataHandler.updateProject();
        return Response.status(200).entity("").build();
    }

    @POST
    @Path("clear")
    @Produces(MediaType.TEXT_PLAIN)
    public Response clearLanguages(@FormParam("uuid") String languageUuid) {
        DataHandler.updateProject();
        DataHandler.readProjectByUuid(languageUuid).clearLanguages();
        return Response.status(200).entity("").build();
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
