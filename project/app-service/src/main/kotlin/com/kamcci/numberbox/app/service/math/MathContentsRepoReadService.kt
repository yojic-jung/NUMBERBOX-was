package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathContentsRepoReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadUseCase
import java.util.*

@UseCase
class MathContentsRepoReadService(
    private val mathContentsRepoReadOrmPort: MathContentsRepoReadOrmPort
) : MathContentsRepoReadUseCase {
    override fun readContentsIdByMemberId(memberId: UUID): List<Long> =
        mathContentsRepoReadOrmPort.readContentsIdByMemberId(memberId)

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean =
        mathContentsRepoReadOrmPort.existByContentsIdAndMemberId(contentsId, memberId)
}