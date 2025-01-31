package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.EmailCheck

/**
 * 비밀번호 찾기 request
 */
data class PasswordFindRequest(
    @field:EmailCheck
    val email: String,
)