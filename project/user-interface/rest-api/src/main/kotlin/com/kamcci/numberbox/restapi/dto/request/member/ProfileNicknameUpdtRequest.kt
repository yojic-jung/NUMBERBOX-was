package com.kamcci.numberbox.restapi.dto.request.member

import org.hibernate.validator.constraints.Length

/**
 * 사용자 프로필 닉네임 변경 요청
 */
data class ProfileNicknameUpdtRequest(
    @field:Length(min = 1, max = 34, message = "닉네임은 최소 1글자 이상 34글자 이하입니다.")
    val nickname: String
)