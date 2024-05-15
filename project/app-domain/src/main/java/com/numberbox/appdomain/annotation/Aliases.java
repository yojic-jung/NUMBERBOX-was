package com.numberbox.appdomain.annotation;

import java.lang.annotation.*;

/**
 * Def. 객체의 별칭
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Aliases {
    String value() default "";
}