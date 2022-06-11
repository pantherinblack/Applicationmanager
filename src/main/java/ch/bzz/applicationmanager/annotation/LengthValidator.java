package ch.bzz.applicationmanager.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * Validator class to check if parameters are correct.
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 11.06.2022
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class LengthValidator implements ConstraintValidator<Length, Object[]> {

    /**
     * checks if the parameters are valid.
     *
     * @param objs,   parameters to be checked. objs[0] -> name, objs[1] -> short
     * @param context unused.
     * @return valid -> true, invalid ->false.
     */
    @Override
    public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
        if (objs[0] == null || objs[1] == null) return true;
        if (!(objs[0] instanceof String) || !(objs[1] instanceof String)) {
            throw new IllegalArgumentException("Illegal method signature, expected two parameters of type String");
        }
        return ((String) objs[0]).length() >= ((String) objs[1]).length();
    }
}