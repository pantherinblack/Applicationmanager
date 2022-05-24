package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Type;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Service for the type model, able to give back a un/filtered list or to search for the first type with
 * the uuid or to search for the first object with the requested name.
 *
 * @author Kevin Stupar
 * @version 1.6
 * @since 24.05.2022
 */
@Path("type")
public class TypeService {

    /**
     * gives back a list of type objects, can be filtered.
     *
     * @param filter to be searched for (not case-sensitive).
     * @return list of types.
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTypes(@QueryParam("contains") String filter) {
        List<Type> types = DataHandler.getInstance().readAllTypes();
        if (filter != null && !filter.isEmpty()) {
            types.removeIf(type -> !type.getTypeName().toUpperCase().contains(filter.toUpperCase()));
        }
        return Response.status(200).entity(types).build();
    }

    /**
     * gives back a specific type, first object with the requested uuid.
     *
     * @param typeUuid to search for.
     * @return type
     */
    @GET
    @Path("readuuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTypeByUuid(@QueryParam("uuid") String typeUuid) {
        if (typeUuid == null || typeUuid.isEmpty()) return Response.status(400).entity(null).build();
        Type type = DataHandler.getInstance().readTypesByUuid(typeUuid);
        if (type == null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(type).build();
    }

    /**
     * gives back a specific type, first object with the requested nmae.
     *
     * @param typeName to search for.
     * @return type
     */
    @GET
    @Path("readname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTypeByName(@QueryParam("name") String typeName) {
        if (typeName == null || typeName.isEmpty()) return Response.status(400).entity(null).build();
        Type type = DataHandler.getInstance().readTypesByName(typeName);
        if (type == null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(type).build();
    }
}
