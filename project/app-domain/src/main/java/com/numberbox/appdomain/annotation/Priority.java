package com.numberbox.appdomain.annotation;

import java.lang.annotation.*;

/**
 * Def. 같은 타입의 객체 중 의존 주입에 우선권을 갖음
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Priority {
}
