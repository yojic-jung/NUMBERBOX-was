package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import java.util.*

@UseCase
class MathContentsLikeReadService(
    private val mathConLikeReadOrmPort: MathContentsLikeReadOrmPort,
) : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean =
        mathConLikeReadOrmPort.existByContentsIdAndMemberId(contentsId, memberId)
}