package com.kamcci.numberbox.app.domain.dto.member

import java.util.*

/**
 * 사용자 프로필 이미지 수정 dto
 */
data class MemberProfileImgUpdtDto(
    val memberId: UUID,
    val profileImgPath: String,
    val profileImgName: String,
)