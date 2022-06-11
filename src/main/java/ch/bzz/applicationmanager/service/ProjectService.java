package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Project;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
     *
     * @param projectUuid to search for.
     * @return project
     */
    @Path("readuuid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByUuid(
            @NotBlank @QueryParam("projectUuid") String projectUuid
    ) {
        Project project = DataHandler.readProjectByUuid(projectUuid);
        if (project == null) return Response.status(400).entity(null).build();
        return Response.status(200).entity(project).build();
    }

    /**
     * gives back a specific project, first object with the requested name.
     *
     * @param projectName to search for.
     * @return project
     */
    @Path("readname")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByName(
            @Size(min = 1, max = 50)
            @QueryParam("projectName") String projectName
    ) {
        Project project = DataHandler.readProjectByName(projectName);
        if (project == null) return Response.status(400).entity(null).build();
        return Response.status(200).entity(project).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createProject(
            @Valid @BeanParam Project project
    ) {
        int httpStatus = 200;
        project.setProjectUuid(UUID.randomUUID().toString());
        DataHandler.insertProject(project);
        return Response.status(httpStatus).entity("").build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateProject(
            @NotBlank @FormParam("projectUuid") String projectUuid,
            @Valid @BeanParam Project project
    ) {
        int httpStatus = 400;
        Project oldProject = DataHandler.readProjectByUuid(projectUuid);
        if (oldProject != null) {
            oldProject.setProjectName(project.getProjectName());
            oldProject.setProjectVersion(project.getProjectVersion());
            oldProject.setProjectAuthor(project.getProjectAuthor());
            DataHandler.updateProject();
            httpStatus = 200;
        }

        return Response.status(httpStatus).entity("").build();
    }

    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLanguage(
            @FormParam("projectUuid") @NotBlank String projectUuid,
            @FormParam("languageUuid") @NotBlank String language
    ) {
        int httpStatus = 400;
        Project project = DataHandler.readProjectByUuid(projectUuid);
        if (project != null) {
            httpStatus = 200;
            project.addLanguage(language);
            DataHandler.updateProject();
        }
        return Response.status(httpStatus).entity("").build();
    }

    @DELETE
    @Path("clear")
    @Produces(MediaType.TEXT_PLAIN)
    public Response clearLanguages(
            @NotBlank @QueryParam("projectUuid") String projectUuid
    ) {
        int httpStatus = 400;
        Project project = DataHandler.readProjectByUuid(projectUuid);
        if (project != null) {
            httpStatus = 200;
            project.clearLanguages();
            DataHandler.updateProject();
        }
        return Response.status(httpStatus).entity("").build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProject(
            @NotBlank @QueryParam("projectUuid") String typeUuid
    ) {
        int httpStatus = 410;
        if (DataHandler.deleteProject(typeUuid)) {
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity("").build();
    }

}
