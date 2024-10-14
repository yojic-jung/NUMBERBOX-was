package com.kamcci.numberbox.app.domain.dto.math

import java.util.*

/**
 * 문제 좋아요 목록에 저장
 */
data class MathContentsLikeModifyDto(
    val contentsId: Long,
    val memberId: UUID,
)