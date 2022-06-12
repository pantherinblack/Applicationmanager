package ch.bzz.applicationmanager.annotation;

import ch.bzz.applicationmanager.data.DataHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validator check if the uuid exists.
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 11.06.2022
 */
public class ExistingUuidValidator implements ConstraintValidator<ExistingUuid, String> {

    /**
     * validates, if the uuid isn't null and exists.
     *
     * @param value,  uuid of the object.
     * @param context unused.
     * @return existing -> true, not existing -> false.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return (DataHandler.readProjectByUuid(value) != null ||
                DataHandler.readLanguageByUuid(value) != null ||
                DataHandler.readTypesByUuid(value) != null);
    }
}
