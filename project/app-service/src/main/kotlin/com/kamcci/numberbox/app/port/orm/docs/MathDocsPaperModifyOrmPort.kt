package com.kamcci.numberbox.app.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import java.util.*

/**
 * 학습지 변경
 */
interface MathDocsPaperModifyOrmPort {
    fun create(memberId: UUID, createDto: MathDocsPapaerCreateDto): Long
}