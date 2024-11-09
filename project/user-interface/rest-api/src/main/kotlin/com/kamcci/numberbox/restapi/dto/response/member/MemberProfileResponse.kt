package com.kamcci.numberbox.restapi.dto.response.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType

data class MemberProfileResponse(
    val id: Long,
    val nickname: String,
    val profileImgName: String?,
    val profileImgPath: String?,
    val profileType: ProfileType
)