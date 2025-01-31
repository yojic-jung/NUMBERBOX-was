package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import java.util.*

class MockMathContentsReadCase : MathContentsReadCase {
    override fun readById(contentsId: Long): MathContentsVo? {
        TODO("Not yet implemented")
    }

    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> {
        TODO("Not yet implemented")
    }

    override fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? {
        TODO("Not yet implemented")
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        TODO("Not yet implemented")
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        TODO("Not yet implemented")
    }

    override fun readDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        TODO("Not yet implemented")
    }

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? {
        TODO("Not yet implemented")
    }

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? {
        TODO("Not yet implemented")
    }

    override fun readTransContCntById(id: Long): Int? {
        TODO("Not yet implemented")
    }

    override fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo? {
        TODO("Not yet implemented")
    }

    override fun countByUnitId(unitId: List<Int>): Long {
        TODO("Not yet implemented")
    }

    override fun existById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

}