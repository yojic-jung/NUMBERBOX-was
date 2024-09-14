package com.kamcci.numberbox.app.repository.member

/**
 * Def. 이메일 검증 코드 영속화
 */
interface EmailIDCodeQueryRepository {
    fun countByEmail(email: String): Long
}