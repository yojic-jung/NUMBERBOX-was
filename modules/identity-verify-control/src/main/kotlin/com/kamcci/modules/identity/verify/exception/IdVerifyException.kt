package com.kamcci.modules.identity.verify.exception

/**
 * 본인인증 모듈 예외
 */
class IdVerifyException(override val message: String?, val code: Int) : RuntimeException(message)