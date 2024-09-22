package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpResultVo

/**
 * 회원가입 양식 검증
 */
interface MemberSignupValidator {
    fun validate(signUpDto: MemberSignUpDto): MemberSignUpResultVo?
}