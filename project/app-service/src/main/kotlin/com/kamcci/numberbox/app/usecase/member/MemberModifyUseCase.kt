package com.kamcci.numberbox.app.usecase.member

/**
 * 사용자 정보 변경
 */
interface MemberModifyUseCase {

    /**
     * 비밀번호 변경
     */
    fun updatePasswd(passwd: String)

    /**
     * 회원 탈퇴
     */
    fun drop()

}