package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageWriteOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import java.util.*

@UseCase
class MathDocsUsageWriteService(
    private val mathDocsUsageWriteOrmPort: MathDocsUsageWriteOrmPort
) : MathDocsUsageWriteCase {
    companion object {
        const val NOT_SAVED = "학습지 사용 로그가 저장 되지 않았습니다."
    }

    @TXExecute
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        val id = mathDocsUsageWriteOrmPort.create(memberId, createDto)
        if (id == 0L) throw BusinessInValidException(NOT_SAVED)
        return id
    }

}