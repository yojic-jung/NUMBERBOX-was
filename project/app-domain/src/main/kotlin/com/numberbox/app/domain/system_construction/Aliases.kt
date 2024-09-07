package com.numberbox.app.domain.system_construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Def. 상위 타입의 하위 구현체들에게 각각 별칭을 주기 위해 사용할 어노테이션
 * Desc. 스프링의 @Qualifier 역할
 */
@Target(ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
annotation class Aliases(val value: String = "")
