package com.kamcci.numberbox.app.usecase.member

/**
 * 계정 찾기
 */
interface MemberFindUseCase {
    /**
     * 이메일 찾기
     */
    fun readMyEmail(userName: String, phoneNumber: String): String?

    /**
     * 비밀번호 찾기
     */
    fun readMyPassword(email: String)
}