package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsOnlyVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsReadRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.math.MathContentsExpression
import java.util.UUID

class MockMathContentsReadRepository: MathContentsReadRepository(MathContentsExpression()) {
    override fun readById(contentsId: Long): MathContentsVo? {
        return null
    }

    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> {
        return listOf()
    }


    override fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? {
       return null
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return listOf()
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return listOf()
    }

    override fun readDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return listOf()
    }

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? {
        return null
    }

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? {
        return null
    }

    override fun readTransContCntById(id: Long): Int? {
        return null
    }

    override fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo? {
        return null
    }


    override fun countByUnitId(unitId: List<Int>): Long {
        return 1L
    }

    override fun existById(id: Long): Boolean {
        return true
    }
}