package net.therap.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sadia.afroz
 * @since 3/24/21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    int min() default 1;

    int max() default 100;

    String message() default "Length must be {min}-{max}";

    Class validatorClass() default Integer.class;
}