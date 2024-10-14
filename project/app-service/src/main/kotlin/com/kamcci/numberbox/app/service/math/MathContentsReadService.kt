package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.port.repository.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsReadUseCase
import java.util.*

@UseCase
class MathContentsReadService(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort
) : MathContentsReadUseCase {
    override fun findByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsVo> {
        return mathContentsReadOrmPort.findByUnitId(memberId, unitId, pageReq)
    }

    override fun countByUnitId(unitId: List<Int>): Long {
        return mathContentsReadOrmPort.countByUnitId(unitId)
    }

    override fun existById(id: Long): Boolean {
        return mathContentsReadOrmPort.existById(id)
    }
}