package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import java.util.*

/**
 * 학습지 정보 조회
 */
interface MathDocsPaperReadUseCase {
    // 학습지 정보 조회
    fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo?
}