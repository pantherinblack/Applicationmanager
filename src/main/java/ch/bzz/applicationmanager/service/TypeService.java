package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Type;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

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
        List<Type> types = DataHandler.readAllTypes();
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
        Type type = DataHandler.readTypesByUuid(typeUuid);
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
        Type type = DataHandler.readTypesByName(typeName);
        if (type == null) return Response.status(404).entity(null).build();
        return Response.status(200).entity(type).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createType(
            @FormParam("name") String name,
            @FormParam("desc") String description
    ) {
        int httpStatus = 200;
        if (name != null && !name.isEmpty() && description != null && !description.isEmpty()) {
            Type type = new Type();
            type.setTypeUuid(UUID.randomUUID().toString());
            type.setTypeName(name);
            type.setTypeDescription(description);
            DataHandler.insertType(type);
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
