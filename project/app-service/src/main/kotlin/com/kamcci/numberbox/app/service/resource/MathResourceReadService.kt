package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadUseCase

@UseCase
class MathResourceReadService(
    private val mathResourceReadOrmPort: MathResourceReadOrmPort
) : MathResourceReadUseCase {
    override fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo> =
        mathResourceReadOrmPort.readByMainCateId(mainCateId, pageReq)

    override fun countByMainCateId(mainCateId: Int): Long =
        mathResourceReadOrmPort.countByMainCateId(mainCateId)
}