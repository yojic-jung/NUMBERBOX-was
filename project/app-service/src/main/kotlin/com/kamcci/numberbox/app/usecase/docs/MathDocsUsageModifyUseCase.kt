package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import java.util.*

/**
 * 학습지 제작 기능 사용량 - 변경
 */
interface MathDocsUsageModifyUseCase {
    // 학습지 제작 기능 사용 생성
    fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long

}