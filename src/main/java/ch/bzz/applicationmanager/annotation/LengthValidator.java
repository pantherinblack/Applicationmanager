package ch.bzz.applicationmanager.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class LengthValidator implements ConstraintValidator<Length, Object[]> {
    public void initialize(Length constraint) {
    }

    @Override
    public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
        if (objs[0] == null || objs[1] == null) return true;
        if (!(objs[0] instanceof String) || !(objs[1] instanceof String)) {
            throw new IllegalArgumentException("Illegal method signature, expected two parameters of type String");
        }
        return ((String) objs[0]).length() >= ((String) objs[1]).length();
    }
}