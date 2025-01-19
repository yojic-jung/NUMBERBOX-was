package com.kamcci.modules.auth.control.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * - 해당 어노테이션을 메서드 파라미터에 지정시 사용자 id를 주입 받을 수 있음
 * - Handler Resolver를 통해 주입됨
 */
@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UserId {
    String ATTR_NAME = "userId";
}
