package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathCategoryTypeReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadUseCase

@UseCase
class MathCategoryTypeReadService(
    private val mathCategoryTypeReadOrmPort: MathCategoryTypeReadOrmPort
) : MathCategoryTypeReadUseCase {
    override fun readByUnitId(unitId: Int) = mathCategoryTypeReadOrmPort.readByUnitId(unitId)

    override fun readByUnitId(unitIdList: List<Int>) = mathCategoryTypeReadOrmPort.readByUnitId(unitIdList)
}