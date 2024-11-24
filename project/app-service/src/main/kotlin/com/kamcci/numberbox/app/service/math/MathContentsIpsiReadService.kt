package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsIpsiReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsIpsiReadCase

@UseCase
class MathContentsIpsiReadService(
    private val mathContentsIpsiReadOrmPort: MathContentsIpsiReadOrmPort
) : MathContentsIpsiReadCase {
    override fun readAllIpsiYear(): List<Int> =
        mathContentsIpsiReadOrmPort.readAllIpsiYear()
}