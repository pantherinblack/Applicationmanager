package ch.bzz.applicationmanager.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception class, which sends a 400 http response with a matching message.
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 11.06.2022
 */
@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    /**
     * generates the response.
     *
     * @param exception exception to have occurred.
     * @return 400-response.
     */
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    /**
     * generates the message for the response.
     *
     * @param exception exception th have occurred.
     * @return message.
     */
    private String prepareMessage(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder("Service failed. Cause:\n");
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            msg.append("\t").append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }
        return msg.toString();
    }
}