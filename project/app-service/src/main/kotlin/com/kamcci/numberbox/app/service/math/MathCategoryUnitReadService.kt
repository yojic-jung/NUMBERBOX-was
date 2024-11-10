package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.port.orm.math.MathCategoryUnitReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadUseCase

@UseCase
class MathCategoryUnitReadService(
    private val mathCategoryUnitReadOrmPort: MathCategoryUnitReadOrmPort
) : MathCategoryUnitReadUseCase {
    override fun readAll(): List<MathCategoryUnitVo> = mathCategoryUnitReadOrmPort.readAll()
}