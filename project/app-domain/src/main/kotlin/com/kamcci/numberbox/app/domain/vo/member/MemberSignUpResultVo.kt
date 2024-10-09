package com.kamcci.numberbox.app.domain.vo.member

import java.util.*

/**
 * 회원가입 결과 반환
 */
data class MemberSignUpResultVo(
    // 성공시에만 반환
    val uuid: UUID? = null,
    val email: String? = null,
    val roles: List<String>? = null,
)
