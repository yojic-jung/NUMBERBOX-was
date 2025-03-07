package com.kamcci.numberbox.app.domain.vo.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.util.*

/**
 * 회원 프로필 정보
 */
data class MemberProfileVo(
    val id: Long,
    val memberId: UUID,
    val nickname: String,
    val profileImgName: String?,
    val profileImgPath: String?,
    val profileType: ProfileType
)