package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberDropDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo

/**
 * 사용자 정보 변경
 */
interface MemberModifyUseCase {
    /**
     * 비밀번호 변경
     */
    fun updatePassword(updtDto: MemberPasswdUpdtDto): Boolean

    /**
     * 회원가입
     */
    fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo

    /**
     * 회원 탈퇴
     */
    fun drop(dropDto: MemberDropDto): Boolean
}