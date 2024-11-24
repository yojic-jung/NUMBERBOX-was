package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceMenuReadOrmPort
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase

@UseCase
class MathResourceMenuReadService(
    private val mathResourceMenuReadOrmPort: MathResourceMenuReadOrmPort
) : MathResourceMenuReadCase {

    override fun readAll(): List<MathResourceMenuVo> {
        return mathResourceMenuReadOrmPort.readAll()
    }
}