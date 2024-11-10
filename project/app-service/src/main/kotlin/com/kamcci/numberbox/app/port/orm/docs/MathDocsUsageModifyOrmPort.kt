package com.kamcci.numberbox.app.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import java.util.*

/**
 * 학습지 제작 기능 사용량 - 변경
 */
interface MathDocsUsageModifyOrmPort {
    // 학습지 제작 기능 사용 생성
    fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long

}
