package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.*
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo

/**
 * 사용자 정보 변경
 */
interface MemberModifyUseCase {

    /**
     * 비밀번호 변경
     */
    fun updatePassword(passwordUpdtDto: MemberPasswdUpdtDto): Boolean

    /**
     * 휴대폰 번호 변경
     */
    fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean

    /**
     * 회원가입
     */
    fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo

    /**
     * 회원 탈퇴
     */
    fun drop(dropDto: MemberDropDto): Boolean

}