package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.module.Type;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    public Response listTypes(
            @QueryParam("contains") String filter
    ) {
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
    public Response readTypeByUuid(
            @NotBlank @QueryParam("typeUuid") String typeUuid
    ) {
        Type type = DataHandler.readTypesByUuid(typeUuid);
        if (type == null) return Response.status(400).entity(null).build();
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
    public Response readTypeByName(
            @NotBlank @Size(min = 2, max = 50) @QueryParam("typeName") String typeName
    ) {
        Type type = DataHandler.readTypesByName(typeName);
        if (type == null) return Response.status(400).entity(null).build();
        return Response.status(200).entity(type).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createType(
            @Valid @BeanParam Type type
    ) {
        type.setTypeUuid(UUID.randomUUID().toString());
        DataHandler.insertType(type);
        return Response.status(200).entity("").build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateType(
            @NotBlank @FormParam("typeUuid") String typeUuid,
            @Valid @BeanParam Type type
    ) {
        Type oldType = DataHandler.readTypesByUuid(typeUuid);
        int httpStatus = 400;
        if (oldType != null) {
            oldType.setTypeName(type.getTypeName());
            oldType.setTypeDescription(type.getTypeDescription());
            DataHandler.updateType();
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity("").build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteType(
            @NotBlank @QueryParam("typeUuid") String typeUuid
    ) {
        int httpStatus = 410;
        if (DataHandler.deleteType(typeUuid)) {
            httpStatus = 200;
        }
        return Response.status(httpStatus).entity("").build();
    }
}
