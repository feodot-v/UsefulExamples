package com.celesio.filetransferagent.validation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Flag {
    String DEFAULT_FLAG_NAME = "defaultFlagName";

    @AliasFor("flagName")
    String value() default DEFAULT_FLAG_NAME;

    @AliasFor("value")
    String flagName() default DEFAULT_FLAG_NAME;

    String flagValue();
}
