package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.port.orm.math.MathFormulaKeyReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadCase

@UseCase
class MathFormulaKeyReadService(
    private val mathFormulaKeyReadOrmPort: MathFormulaKeyReadOrmPort
) : MathFormulaKeyReadCase {

    override fun readAll(): List<MathFormulaKeyVo> = mathFormulaKeyReadOrmPort.readAll()
}