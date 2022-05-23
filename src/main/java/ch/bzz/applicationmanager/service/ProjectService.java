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

@Path("project")
public class ProjectService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProjects(@QueryParam("contains") String filter) {
        List<Project> projects = DataHandler.getInstance().readAllProjects();
        if (filter != null && !filter.isEmpty()) {
            projects.removeIf(project -> !project.getProjectName().toUpperCase().contains(filter.toUpperCase()));
        }
        return Response.status(200).entity(projects).build();
    }

    @Path("readuuid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByUuid(@QueryParam("uuid")String projectUuid) {
        if (projectUuid == null || projectUuid.isEmpty()) return Response.status(400).entity(null).build();
        Project project = DataHandler.getInstance().readProjectByUuid(projectUuid);
        if (project==null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(project).build();
    }

    @Path("readname")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProjectByName(@QueryParam("name")String projectName) {
        if (projectName == null || projectName.isEmpty()) return Response.status(400).entity(null).build();
        Project project = DataHandler.getInstance().readProjectByName(projectName);
        if (project==null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(project).build();
    }

}
