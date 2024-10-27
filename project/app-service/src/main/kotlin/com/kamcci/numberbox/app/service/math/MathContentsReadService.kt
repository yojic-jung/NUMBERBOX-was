package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
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

    override fun findById(contentsId: Long): MathContentsVo? =
        mathContentsReadOrmPort.findById(contentsId)


    override fun findById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> =
        mathContentsReadOrmPort.findById(contentsId, pageReq)

    override fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo> =
        mathContentsReadOrmPort.findByProfileId(profileId, pageReq)

    override fun findDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? =
        mathContentsReadOrmPort.findDetailByIdAndMemberId(id, memberId)

    override fun findDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> =
        mathContentsReadOrmPort.findDetailByMemberId(memberId, svcPosbSttsType, pageReq)

    override fun findInHouseContentsById(contentsId: Long): MathInHouseContentsVo? =
        mathContentsReadOrmPort.findInHouseContentsById(contentsId)

    override fun findIpsiContentsById(contentsId: Long): MathIpsiContentsVo? =
        mathContentsReadOrmPort.findIpsiContentsById(contentsId)


    override fun findDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest) =
        mathContentsReadOrmPort.findDetailByUnitId(memberId, unitId, pageReq)

    override fun findContentsOnly(contentsId: Long, memberId: UUID) =
        mathContentsReadOrmPort.findContentsOnly(contentsId, memberId)

    override fun countByUnitId(unitId: List<Int>) = mathContentsReadOrmPort.countByUnitId(unitId)

    override fun existById(id: Long): Boolean = mathContentsReadOrmPort.existById(id)
}