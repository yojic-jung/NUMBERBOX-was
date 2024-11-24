package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdConfirmDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import java.util.*

/**
 * 사용자 정보 변경
 */
interface MemberWriteUseCase {
    /**
     * 비밀번호 변경
     */
    fun updatePassword(updtDto: MemberPasswdUpdtDto): Boolean

    /**
     * 비밀번호 검증
     */
    fun confirmPassword(confirmDto: MemberPasswdConfirmDto): Boolean

    /**
     * 회원가입
     */
    fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo

    /**
     * 회원 탈퇴
     */
    fun drop(memberId: UUID)

    /**
     * 임시 비밀번호 발급 만료자 신규 비밀번호로 변경
     */
    fun updateTmpPassword(id: List<UUID>)

}