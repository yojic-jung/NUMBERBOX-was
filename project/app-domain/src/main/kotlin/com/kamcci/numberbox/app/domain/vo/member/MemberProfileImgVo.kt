package com.kamcci.numberbox.app.domain.vo.member

import java.util.*

/**
 * 사용자 프로필 이미지 정보
 */
data class MemberProfileImgVo(
    val id: Long,
    val memberId: UUID,
    val profileImgPath: String?,
    val profileImgName: String?,
)