package ch.bzz.applicationmanager.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation class for checking if the uuid exists.
 *
 * @author Kevin Stupar
 * @version 1.0
 * @since 11.06.2022
 */
@Constraint(validatedBy = ExistingUuidValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.PARAMETER})
public @interface ExistingUuid {
    /**
     * gives back the message, if the validation fails.
     *
     * @return message.
     */
    String message() default
            "is not an existing uuid";

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
