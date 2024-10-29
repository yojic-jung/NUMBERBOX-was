package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathContentsIpsiReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsIpsiReadUseCase

@UseCase
class MathContentsIpsiReadService(
    private val mathContentsIpsiReadOrmPort: MathContentsIpsiReadOrmPort
) : MathContentsIpsiReadUseCase {
    override fun findAllIpsiYear(): List<Int> =
        mathContentsIpsiReadOrmPort.findAllIpsiYear()
}