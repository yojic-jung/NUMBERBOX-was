package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto

/**
 * 문제 좋아요 - 변경
 */
interface MathContentsLikeWriteUseCase {
    // 좋아요 목록 저장
    fun save(modifyDto: MathContentsLikeModifyDto)

    // 좋아요 목록에서 제거
    fun delete(modifyDto: MathContentsLikeModifyDto)
}