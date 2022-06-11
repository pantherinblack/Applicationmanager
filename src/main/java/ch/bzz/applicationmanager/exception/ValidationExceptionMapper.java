package ch.bzz.applicationmanager.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        String msg = "Service failed. Cause:\n";
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            msg += "\t" + cv.getPropertyPath() + " " + cv.getMessage() + "\n";
        }
        return msg;
    }
}