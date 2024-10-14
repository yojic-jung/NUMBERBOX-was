package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import java.util.*

/**
 * 수학문제 조회
 */
interface MathContentsReadOrmPort {
    // 단원으로 수학문제 조회
    fun findByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsVo>

    // 단원으로 수학문제 카운트
    fun countByUnitId(unitId: List<Int>): Long

    // 수학문제 id 존재 여부
    fun existById(id: Long): Boolean
}