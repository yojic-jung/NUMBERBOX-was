package com.kamcci.numberbox.app.port.orm.math

import java.util.*

/**
 * 문제 좋아요 - 조회
 */
interface MathContentsLikeReadOrmPort {
    // 존재여부
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean
}