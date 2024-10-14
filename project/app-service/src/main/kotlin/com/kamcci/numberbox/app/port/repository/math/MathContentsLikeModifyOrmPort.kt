package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto

/**
 * 문제 좋아요 목록 - 변경
 */
interface MathContentsLikeModifyOrmPort {
    // 좋아요 목록 저장
    fun save(modifyDto: MathContentsLikeModifyDto): Boolean

    // 좋아요 목록에서 제거
    fun delete(modifyDto: MathContentsLikeModifyDto): Boolean
}