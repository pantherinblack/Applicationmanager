package ch.bzz.applicationmanager.service;

import ch.bzz.applicationmanager.annotation.ExistingUuid;
import ch.bzz.applicationmanager.data.DataHandler;
import ch.bzz.applicationmanager.data.UserData;
import ch.bzz.applicationmanager.module.Type;
import ch.bzz.applicationmanager.uil.AESEncrypt;

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
            @CookieParam("complete") String complete,
            @QueryParam("contains") String filter
    ) {
        int httpStatus = 403;
        List<Type> types = null;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            types = DataHandler.readAllTypes();
            if (filter != null && !filter.isEmpty()) {
                types.removeIf(type -> !type.getTypeName().toUpperCase().contains(filter.toUpperCase()));
            }
            httpStatus = 200;
        }

        return Response.status(httpStatus).entity(types).build();
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
            @CookieParam("complete") String complete,
            @ExistingUuid @QueryParam("typeUuid") String typeUuid
    ) {
        int httpStatus = 403;
        Type type = null;
        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            type = DataHandler.readTypesByUuid(typeUuid);
            httpStatus = 200;
            if (type == null) httpStatus = 404;
        }


        return Response.status(httpStatus).entity(type).build();
    }

    /**
     * gives back a specific type, first object with the requested name.
     *
     * @param typeName to search for.
     * @return type
     */
    @GET
    @Path("readname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTypeByName(
            @CookieParam("complete") String complete,
            @NotBlank @Size(min = 2, max = 50) @QueryParam("typeName") String typeName
    ) {
        int httpStatus = 403;
        Type type = null;

        if (UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.USER)) {
            type = DataHandler.readTypesByName(typeName);
            httpStatus = 200;
            if (type == null) httpStatus = 200;
        }
        return Response.status(httpStatus).entity(type).build();
    }

    /**
     * creates a type.
     *
     * @param type to be created.
     * @return nothing as a response.
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createType(
            @CookieParam("complete") String complete,
            @Valid @BeanParam Type type
    ) {
        int httpStatus = 404;


        if (!UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) {
            httpStatus = 403;
        } else {
            if (!DataHandler.isExistingType(type)) {
                type.setTypeUuid(UUID.randomUUID().toString());
                DataHandler.insertType(type);
                httpStatus = 200;
            }
        }
        return Response.status(httpStatus).entity("").build();
    }

    /**
     * changes the values of a type.
     *
     * @param typeUuid        to which the changes should be done.
     * @param type-attributes to be changed to.
     * @return nothing as a response.
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateType(
            @CookieParam("complete") String complete,
            @ExistingUuid @FormParam("typeUuid") String typeUuid,
            @Valid @BeanParam Type type
    ) {
        int httpStatus = 400;
        if (!UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) {
            httpStatus = 403;
        } else {
            if (!DataHandler.isExistingType(type)) {
                Type oldType = DataHandler.readTypesByUuid(typeUuid);
                oldType.setTypeName(type.getTypeName());
                oldType.setTypeDescription(type.getTypeDescription());
                DataHandler.updateType();
                httpStatus = 200;
            }
        }

        return Response.status(httpStatus).entity("").build();
    }

    /**
     * deletes a type
     *
     * @param typeUuid to be deleted.
     * @return nothing as a response.
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteType(
            @CookieParam("complete") String complete,
            @ExistingUuid @QueryParam("typeUuid") String typeUuid
    ) {
        int httpStatus = 200;

        if (!UserData.userAllowed(AESEncrypt.decrypt(complete), UserData.ADMIN)) httpStatus = 403;
        DataHandler.deleteType(typeUuid);
        return Response.status(httpStatus).entity("").build();
    }
}
