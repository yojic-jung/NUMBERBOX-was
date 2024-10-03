package com.kamcci.numberbox.app.port.repository.member

/**
 * 내 계정 찾기
 */
interface MemberFindUseCase {
    /**
     * 내 이메일 찾기
     */
    fun findMyEmail()

    /**
     * 내 비밀번호 찾기
     */
    fun findMyPassword(): Boolean
}