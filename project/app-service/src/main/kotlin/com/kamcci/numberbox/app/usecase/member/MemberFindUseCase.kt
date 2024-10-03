package com.kamcci.numberbox.app.usecase.member

/**
 * 계정 찾기
 */
interface MemberFindUseCase {
    /**
     * 이메일 찾기
     */
    fun findMyEmail(userName: String, phoneNumber: String): String?

    /**
     * 비밀번호 찾기
     *
     * 응답값
     * true: 이메일로 임시번호 발급
     * false : 이메일 미존재
     */
    fun findMyPassword(email: String): Boolean
}