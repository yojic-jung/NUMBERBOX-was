package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathLikeCountVo
import java.util.*

/**
 * 문제 좋아요 - 조회
 */
interface MathContentsLikeReadCase {
    // 좋아요 존재여부
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean

    // 좋아요 카운트 반환
    fun countBy(contentsId: Long): Long

    // 사용자가 좋아요 누른 contentsId 반환
    fun readContentsIdByUserId(userId: UUID): List<Long>

    fun countBy(contentsIds: List<Long>): List<MathLikeCountVo>
}