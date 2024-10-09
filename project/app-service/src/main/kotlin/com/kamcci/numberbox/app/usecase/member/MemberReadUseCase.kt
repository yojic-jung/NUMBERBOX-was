package com.kamcci.numberbox.app.usecase.member

/**
 * 사용자 정보 조회
 */
interface MemberReadUseCase {
    /**
     * 이메일 존재 여부 조회
     */
    fun existEmail(email: String): Boolean
}