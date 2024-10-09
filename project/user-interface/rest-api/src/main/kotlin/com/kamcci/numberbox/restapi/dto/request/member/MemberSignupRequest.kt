package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.restapi.validation.member.EmailCheck
import com.kamcci.numberbox.restapi.validation.member.PasswordCheck
import jakarta.validation.Valid
import java.util.*

/**
 * 회원 가입 요청 dto
 */
data class MemberSignupRequest(
    // 이메일
    @field:EmailCheck
    val email: String,
    // 비밀번호
    @field:PasswordCheck
    val password: String,
    // 비밀번호 확인
    val confirmPassword: String,
    // 이메일 검증 코드
    val emailVerifyCode: UUID,
    // 개인정보
    @field:Valid
    val privateInfo: MemberPrivateSignupRequest? = null
) {
    init {
        // 비밀번호 검증 확인
        if (password != confirmPassword) {
            throw BusinessValidException("비밀번호가 일치하지 않습니다.")
        }
    }
}
