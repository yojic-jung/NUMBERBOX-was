package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageWriteOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteUseCase
import java.util.*

@UseCase
class MathDocsUsageWriteService(
    private val mathDocsUsageWriteOrmPort: MathDocsUsageWriteOrmPort
) : MathDocsUsageWriteUseCase {

    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        return mathDocsUsageWriteOrmPort.create(memberId, createDto)
    }

}