package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoModifyOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoModifyUseCase

@UseCase
class MathContentsRepoModifyService(
    private val mathConRepoReadOrmPort: MathContentsRepoReadOrmPort,
    private val mathConRepoModifyOrmPort: MathContentsRepoModifyOrmPort
) : MathContentsRepoModifyUseCase {
    companion object {
        // 예외 메시지
        const val ALREADY_EXIST = "이미 저장소 목록에 존재합니다."
        const val NOT_EXIST = "존재하지 않거나 자신의 문제가 아닌 경우 저장소에서 삭제가 불가합니다."
    }

    @TXExecute
    override fun save(modifyDto: MathContentsRepoModifyDto) {
        // 존재여부 체크
        val isExist = mathConRepoReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId)
        if (isExist) throw BusinessValidException(ALREADY_EXIST)

        // 저장
        mathConRepoModifyOrmPort.save(modifyDto)
    }

    @TXExecute
    override fun delete(modifyDto: MathContentsRepoModifyDto) {
        // 존재여부 체크
        val isExist = mathConRepoReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId)
        if (!isExist) throw BusinessValidException(NOT_EXIST)

        // 삭제
        mathConRepoModifyOrmPort.delete(modifyDto)
    }
}