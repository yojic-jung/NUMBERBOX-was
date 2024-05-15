package com.numberbox.appdomain.annotation;

import java.lang.annotation.*;

/**
 * Def. 인스턴스화를 진행함
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseCase {
}
