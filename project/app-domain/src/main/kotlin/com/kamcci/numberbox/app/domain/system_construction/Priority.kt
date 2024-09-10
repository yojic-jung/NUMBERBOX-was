package com.kamcci.numberbox.app.domain.system_construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Def. 상위 타입의 하위 구현체 중 최우선 순위를 주기 위해 사용할 어노테이션
 * Desc. 스프링의 @Primary 역할
 */
@Target(ElementType.TYPE, ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class Priority