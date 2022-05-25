package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

}
