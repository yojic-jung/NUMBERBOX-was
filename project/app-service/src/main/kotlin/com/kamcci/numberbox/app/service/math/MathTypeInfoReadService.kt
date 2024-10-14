package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathTypeInfoReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathTypeInfoReadUseCase

@UseCase
class MathTypeInfoReadService(
    private val mathTypeInfoReadOrmPort: MathTypeInfoReadOrmPort
) : MathTypeInfoReadUseCase {
    override fun findByUnitId(unitId: Int) = mathTypeInfoReadOrmPort.findByUnitId(unitId)

    override fun findByUnitId(unitIdList: List<Int>) = mathTypeInfoReadOrmPort.findByUnitId(unitIdList)
}