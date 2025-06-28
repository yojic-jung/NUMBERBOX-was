package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoWriteCase

@UseCase
class MathContentsRepoWriteService(
    private val mathConRepoModifyOrmPort: MathContentsRepoWriteOrmPort
) : MathContentsRepoWriteCase {
    @TXExecute
    override fun save(modifyDto: MathContentsRepoModifyDto) {
        // 저장
        mathConRepoModifyOrmPort.save(modifyDto)
    }

    @TXExecute
    override fun delete(modifyDto: MathContentsRepoModifyDto) {
        // 삭제
        mathConRepoModifyOrmPort.delete(modifyDto)
    }
}