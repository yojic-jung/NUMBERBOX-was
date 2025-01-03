package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import java.util.*

@UseCase
class MathDocsPaperWriteService(
    private val mathDocsPaperWriteOrmPort: MathDocsPaperWriteOrmPort
) : MathDocsPaperWriteCase {
    companion object {
        const val NOT_SAVED = "학습지가 저장 되지 않았습니다."
        const val NOT_MY_DOCS = "자신이 제작한 학습지가 아닙니다."
    }

    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        val id = mathDocsPaperWriteOrmPort.create(memberId, createDto)
        if (id == 0L) throw BusinessInValidException(NOT_SAVED)
        return id
    }

    @TXExecute
    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto) {
        if (mathDocsPaperWriteOrmPort.update(memberId, updtDto) == 0L) {
            throw BusinessInValidException(NOT_MY_DOCS)
        }
    }

    @TXExecute
    override fun delete(docsId: Long, memberId: UUID) {
        if (mathDocsPaperWriteOrmPort.delete(docsId, memberId) == 0L) {
            throw BusinessInValidException(NOT_MY_DOCS)
        }
    }

}