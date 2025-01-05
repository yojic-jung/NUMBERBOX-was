package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeWriteCase

@UseCase
class MathContentsLikeWriteService(
    private val mathContentsLikeReadCase: MathContentsLikeReadCase,
    private val mathConLikeModifyPort: MathContentsLikeWriteOrmPort
) : MathContentsLikeWriteCase {
    companion object {
        // 예외 메시지
        const val ALREADY_EXIST = "이미 좋아요를 누른 수학 문제입니다."
        const val NOT_EXIST = "존재하지 않거나 자신의 문제가 아닌 경우 좋아요 취소가 불가합니다."
    }

    @TXExecute
    override fun save(modifyDto: MathContentsLikeModifyDto) {
        // 존재여부 체크
        val isExist = mathContentsLikeReadCase.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId)
        if (isExist) throw BusinessInValidException(ALREADY_EXIST)

        // 좋아요
        mathConLikeModifyPort.save(modifyDto)
    }

    @TXExecute
    override fun delete(modifyDto: MathContentsLikeModifyDto) {
        // 존재여부 체크
        val isExist = mathContentsLikeReadCase.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId)
        if (!isExist) throw BusinessInValidException(NOT_EXIST)

        // 좋아요 취소
        mathConLikeModifyPort.delete(modifyDto)
    }
}