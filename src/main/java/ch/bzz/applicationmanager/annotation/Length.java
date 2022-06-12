package ch.bzz.applicationmanager.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation class used to check if a name is equals long or longer than the short-name.
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 11.06.2022
 */
@Constraint(validatedBy = LengthValidator.class)
@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
@Documented
public @interface Length {

    /**
     * gives back the message, if the validation fails.
     *
     * @return message.
     */
    String message() default
            "\"Name\" must not be shorter than the \"short\".";

    /**
     * empty
     *
     * @return nothing.
     */
    Class<?>[] groups() default {};

    /**
     * empty
     *
     * @return nothing.
     */
    Class<? extends Payload>[] payload() default {};
}
