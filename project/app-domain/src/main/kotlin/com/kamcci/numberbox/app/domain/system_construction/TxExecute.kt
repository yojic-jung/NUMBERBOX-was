package com.kamcci.numberbox.app.domain.system_construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Def. 데이터베이스 트랜잭션을 적용 시켜주는 커스텀 어노테이션
 * Desc.
 * - 타입(클래스, 인터페이스)와 메서드에 적용 가능
 */
@Target(ElementType.TYPE, ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class TxExecute
