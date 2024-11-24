package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.port.orm.math.MathCategoryUnitReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase

@UseCase
class MathCategoryUnitReadService(
    private val mathCategoryUnitReadOrmPort: MathCategoryUnitReadOrmPort
) : MathCategoryUnitReadCase {
    override fun readAll(): List<MathCategoryUnitVo> = mathCategoryUnitReadOrmPort.readAll()
}