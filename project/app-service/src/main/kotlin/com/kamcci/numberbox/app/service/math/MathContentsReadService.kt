package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import java.util.*

@UseCase
class MathContentsReadService(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort
) : MathContentsReadCase {

    override fun readById(contentsId: Long): MathContentsVo? =
        mathContentsReadOrmPort.readById(contentsId)


    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> =
        mathContentsReadOrmPort.readById(contentsId, pageReq)

    override fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? =
        mathContentsReadOrmPort.readDetailByIdAndMemberId(id, memberId)

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> =
        mathContentsReadOrmPort.readDetailByMemberId(memberId, svcPosbSttsType, pageReq)

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> =
        mathContentsReadOrmPort.readDetailByMemberId(memberId, myMemberId, svcPosbSttsType, pageReq)

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? =
        mathContentsReadOrmPort.readInHouseContentsById(contentsId)

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? =
        mathContentsReadOrmPort.readIpsiContentsById(contentsId)


    override fun readDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest) =
        mathContentsReadOrmPort.readDetailByUnitId(memberId, unitId, pageReq)

    override fun readContentsOnly(contentsId: Long, memberId: UUID) =
        mathContentsReadOrmPort.readContentsOnly(contentsId, memberId)

    override fun countByUnitId(unitId: List<Int>) = mathContentsReadOrmPort.countByUnitId(unitId)

    override fun existById(id: Long): Boolean = mathContentsReadOrmPort.existById(id)
}