package com.kamcci.numberbox.app.port.orm.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceSaveDto

/**
 * 학습 자료 영속화
 */
interface MathResourceModifyOrmPort {
    // 영속화
    fun create(saveDto: MathResourceSaveDto): Long
}