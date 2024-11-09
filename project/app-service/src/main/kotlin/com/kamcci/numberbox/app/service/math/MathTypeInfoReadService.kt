package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathTypeInfoReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathTypeInfoReadUseCase

@UseCase
class MathTypeInfoReadService(
    private val mathTypeInfoReadOrmPort: MathTypeInfoReadOrmPort
) : MathTypeInfoReadUseCase {
    override fun readByUnitId(unitId: Int) = mathTypeInfoReadOrmPort.readByUnitId(unitId)

    override fun readByUnitId(unitIdList: List<Int>) = mathTypeInfoReadOrmPort.readByUnitId(unitIdList)
}