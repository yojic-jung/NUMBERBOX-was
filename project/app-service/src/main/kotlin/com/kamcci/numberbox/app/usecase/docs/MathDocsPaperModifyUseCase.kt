package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import java.util.*

/**
 * 학습지 변경
 */
interface MathDocsPaperModifyUseCase {
    // 학습지 생성
    fun create(memberId: UUID, createDto: MathDocsPapaerCreateDto): Long
}