package com.kamcci.numberbox.app.usecase.math

import java.util.*

/**
 * 문제 좋아요 - 조회
 */
interface MathContentsLikeReadUseCase {
    // 존재여부
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean
}