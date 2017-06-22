package com.celesio.filetransferagent.validation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.celesio.filetransferagent.validation.Flag.DEFAULT_FLAG_NAME;
import static java.lang.annotation.ElementType.FIELD;

@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredIfFlag {

    @AliasFor("flagName")
    String value() default DEFAULT_FLAG_NAME;

    @AliasFor("value")
    String flagName() default DEFAULT_FLAG_NAME;
}
