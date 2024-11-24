package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageModifyOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteUseCase
import java.util.*

@UseCase
class MathDocsUsageWriteService(
    private val mathDocsUsageModifyOrmPort: MathDocsUsageModifyOrmPort
) : MathDocsUsageWriteUseCase {

    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        return mathDocsUsageModifyOrmPort.create(memberId, createDto)
    }

}