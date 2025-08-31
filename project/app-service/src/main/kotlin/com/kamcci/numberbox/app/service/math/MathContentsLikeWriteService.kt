package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeWriteCase

@UseCase
class MathContentsLikeWriteService(
    private val mathConLikeModifyPort: MathContentsLikeWriteOrmPort
) : MathContentsLikeWriteCase {

    @TXExecute
    override fun save(modifyDto: MathContentsLikeModifyDto) {
        // 좋아요
        mathConLikeModifyPort.save(modifyDto)
    }

    @TXExecute
    override fun delete(modifyDto: MathContentsLikeModifyDto) {
        // 좋아요 취소
        mathConLikeModifyPort.delete(modifyDto)
    }
}