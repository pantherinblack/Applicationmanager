package ch.bzz.applicationmanager.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LengthValidator.class)
@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
@Documented
public @interface Length {

    String message() default
            "\"Name\" must not be shorter than the \"short\".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
