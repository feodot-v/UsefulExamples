package com.celesio.filetransferagent.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Documented
@Constraint(validatedBy = FlagValidator.class)
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlagValidated {
    String message() default "Validation failure";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
