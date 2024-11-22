package com.kamcci.numberbox.app.usecase.member

import java.util.*

/**
 * 사용자 정보 조회
 */
interface MemberReadUseCase {
    /**
     * 이메일 존재 여부 조회
     */
    fun existEmail(email: String): Boolean

    /**
     * 임시 비밀번호 발급자 id 조회
     */
    fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID>
}