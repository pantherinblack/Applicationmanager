package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * test service
 */
@Path("test")
public class TestService {

    /**
     * gives back a testmessage
     *
     * @return message
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            Object[] oPaths = Files.list(Paths.get(Config.getProperty("backup"))).toArray();
            for (Object oPath : oPaths) {
                java.nio.file.Path path = (java.nio.file.Path) oPath;
                Files.copy(path, Paths.get(Paths.get(Config.getProperty("backup")).getParent() + "/" + path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
            DataHandler.initLists();
            return Response.status(200).entity("").build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(404).entity("").build();
        }

    }
}