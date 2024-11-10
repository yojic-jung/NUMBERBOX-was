package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperModifyUseCase
import java.util.*

@UseCase
class MathDocsPaperModifyService(
    private val mathDocsPaperModifyOrmPort: MathDocsPaperModifyOrmPort
) : MathDocsPaperModifyUseCase {
    companion object {
        const val NOT_MY_DOCS = "자신이 제작한 학습지가 아닙니다."
    }

    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long =
        mathDocsPaperModifyOrmPort.create(memberId, createDto)

    @TXExecute
    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto) {
        if (mathDocsPaperModifyOrmPort.update(memberId, updtDto) == 0L) {
            throw BusinessValidException(NOT_MY_DOCS)
        }
    }

    @TXExecute
    override fun delete(docsId: Long, memberId: UUID) {
        if (mathDocsPaperModifyOrmPort.delete(docsId, memberId) == 0L) {
            throw BusinessValidException(NOT_MY_DOCS)
        }
    }

}