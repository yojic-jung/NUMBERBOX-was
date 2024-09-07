package com.numberbox.app.domain.system_construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Def. 의존주입을 가능하게 함
 * Desc. 스프링의 @ComponentScan 역할을 함
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class UseCase