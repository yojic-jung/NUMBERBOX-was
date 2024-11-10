package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperReadOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadUseCase
import java.util.*

@UseCase
class MathDocsPaperReadService(
    private val mathDocsPaperReadOrmPort: MathDocsPaperReadOrmPort
) : MathDocsPaperReadUseCase {
    override fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo? =
        mathDocsPaperReadOrmPort.readByIdAndMemberId(id, memberId)
}