package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import java.util.*

/**
 * 학습지 정보 조회
 */
interface MathDocsPaperReadUseCase {
    // 학습지 정보 조회
    fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo?

    // 학습지 내역 조회
    fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathDocsPaperVo>

    // 사용자가 제작한 학습지 수 조회
    fun countByMemberId(memberId: UUID): Long
}