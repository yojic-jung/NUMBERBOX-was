package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import com.kamcci.numberbox.app.port.repository.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsReadUseCase
import java.util.*

@UseCase
class MathContentsReadService(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort
) : MathContentsReadUseCase {

    override fun findByContentsId(contentsId: Long): MathContentsVo? =
        mathContentsReadOrmPort.findByContentsId(contentsId)


    override fun findByContentsId(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> =
        mathContentsReadOrmPort.findByContentsId(contentsId, pageReq)

    override fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo> =
        mathContentsReadOrmPort.findByProfileId(profileId, pageReq)

    override fun findDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? =
        mathContentsReadOrmPort.findDetailByContentsIdAndMemberId(id, memberId)

    override fun findDetailByMemberId(memberId: UUID, pageReq: PageRequest): List<MathContentsDetailVo> =
        mathContentsReadOrmPort.findDetailByMemberId(memberId, pageReq)

    override fun findInHouseContentsByContentsId(contentsId: Long): MathInHouseContentsVo? =
        mathContentsReadOrmPort.findInHouseContentsByContentsId(contentsId)

    override fun findIpsiContentsByContentsId(contentsId: Long): MathIpsiContentsVo? =
        mathContentsReadOrmPort.findIpsiContentsByContentsId(contentsId)


    override fun findDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest) =
        mathContentsReadOrmPort.findDetailByUnitId(memberId, unitId, pageReq)

    override fun countByUnitId(unitId: List<Int>) = mathContentsReadOrmPort.countByUnitId(unitId)

    override fun existById(id: Long): Boolean = mathContentsReadOrmPort.existById(id)
}