package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathContentsLikeReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadUseCase
import java.util.*

@UseCase
class MathContentsLikeReadService(
    private val mathConLikeReadOrmPort: MathContentsLikeReadOrmPort,
) : MathContentsLikeReadUseCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean =
        mathConLikeReadOrmPort.existByContentsIdAndMemberId(contentsId, memberId)
}