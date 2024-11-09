package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperModifyUseCase
import java.util.*

@UseCase
class MathDocsPaperModifyService(
    private val mathDocsPaperModifyOrmPort: MathDocsPaperModifyOrmPort
) : MathDocsPaperModifyUseCase {
    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsPapaerCreateDto): Long {
        return mathDocsPaperModifyOrmPort.create(memberId, createDto)
    }
}