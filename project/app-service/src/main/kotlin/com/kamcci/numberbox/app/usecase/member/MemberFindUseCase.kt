package com.kamcci.numberbox.app.usecase.member

/**
 * 계정 찾기
 */
interface MemberFindUseCase {
    /**
     * 이메일 찾기
     * todo 임의 난수 코드
     */
    fun findEmail(userName: String, phoneNumber: String): String

    /**
     * 비밀번호 찾기(이메일로 임시 비밀번호 발급)
     */
    fun findPasswd(email: String)
}