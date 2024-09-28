package com.kamcci.numberbox.app.domain.vo.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.util.*

data class MemberProfileVo(
    val id: Long,
    val memberId: UUID,
    val profileImgName: String,
    val profileImgPath: String,
    val profileType: ProfileType
)