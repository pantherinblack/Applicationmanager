package ch.bzz.applicationmanager.uil;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;

/**
 * Class from Marcel Suter
 */
public class LocalDateConverter implements ParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String value) {
        if (value == null)
            return null;
        return LocalDate.parse(value);
    }

    @Override
    public String toString(LocalDate value) {
        if (value == null)
            return null;
        return value.toString();
    }

}