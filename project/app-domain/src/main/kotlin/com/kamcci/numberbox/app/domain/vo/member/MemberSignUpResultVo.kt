package com.kamcci.numberbox.app.domain.vo.member

import java.util.*

/**
 * 회원가입 결과 반환
 */
data class MemberSignUpResultVo(
    val isSuccess: Boolean,
    val messageType: SignUpResultMSg,
    // 성공시에만 반환
    val uuid: UUID? = null,
    val email: String? = null,
    val roles: List<String>? = null,
) {
    enum class SignUpResultMSg(val desc: String) {
        SUCCESS_MSG("회원가입에 성공하였습니다."),
        EXPIRED_MSG("만료된 인증 코드입니다."),
        NOT_MATCH_CODE_MSG("인증 코드가 일치하지 않습니다."),
        EXIST_EMAIL_MSG("이미 존재하는 이메일입니다."),
    }

    fun getMessage() = messageType.desc
}

