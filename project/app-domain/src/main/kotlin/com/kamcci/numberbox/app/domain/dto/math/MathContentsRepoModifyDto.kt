package com.kamcci.numberbox.app.domain.dto.math

import java.util.*

/**
 * 문제 저장소에 저장
 */
data class MathContentsRepoModifyDto(
    val contentsId: Long,
    val memberId: UUID,
)