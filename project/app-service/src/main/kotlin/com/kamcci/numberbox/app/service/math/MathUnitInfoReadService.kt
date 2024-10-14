package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathUnitInfoVo
import com.kamcci.numberbox.app.port.repository.math.MathUnitInfoReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathUnitInfoReadUseCase

@UseCase
class MathUnitInfoReadService(
    private val mathUnitInfoReadOrmPort: MathUnitInfoReadOrmPort
) : MathUnitInfoReadUseCase {
    override fun findAll(): List<MathUnitInfoVo> = mathUnitInfoReadOrmPort.findAll()
}