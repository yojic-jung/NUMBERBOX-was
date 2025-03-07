package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType

/**
 * 사용자 프로필 타입 변경 요청
 */
data class ProfileTypeUpdtRequest(
    val profileType: ProfileType
)