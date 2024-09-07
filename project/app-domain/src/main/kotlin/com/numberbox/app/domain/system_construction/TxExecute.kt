package com.numberbox.app.domain.system_construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * 데이터베이스 트랜잭션 적용
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class TxExecute
