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
    companion object {
        // 예외 메시지
        const val LIKE_SAVE_FAIL = "좋아요 정보 저장되지 않음"
        const val LIKE_DELETE_FAIL = "좋아요 취소되지 않음"
    }

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