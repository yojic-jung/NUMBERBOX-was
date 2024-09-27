package com.kamcci.modules.auth.engine.annotation


/**
 * - 해당 어노테이션을 메서드 파라미터에 지정시 사용자 이메일을 주입 받을 수 있음
 * - Handler Resolver를 통해 주입됨
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserEmail
