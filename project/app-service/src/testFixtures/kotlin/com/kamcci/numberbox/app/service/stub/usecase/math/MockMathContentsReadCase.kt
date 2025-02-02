package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsDetailVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsDetailVoList
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsOnlyVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsVoList
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathInHouseContentsVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathIpsiContentsVo
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import java.util.*

class MockMathContentsReadCase : MathContentsReadCase {
    override fun readById(contentsId: Long): MathContentsVo? {
        return if (contentsId == FAIL_ID) null else getMathContentsVo(contentsId)
    }

    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> {
        return getMathContentsVoList()
    }

    override fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo {
        return getMathContentsDetailVo()
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return getMathContentsDetailVoList()
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return getMathContentsDetailVoList()
    }

    override fun readDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return getMathContentsDetailVoList()
    }

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? {
        return if (contentsId == FAIL_ID) null else getMathInHouseContentsVo()
    }

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? {
        return if (contentsId == FAIL_ID) null else getMathIpsiContentsVo()
    }

    override fun readTransContCntById(id: Long): Int? {
        return if (id == FAIL_ID) null else 1
    }

    override fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo? {
        return if (contentsId == FAIL_ID) null else getMathContentsOnlyVo()
    }

    override fun countByUnitId(unitId: List<Int>): Long {
        return 10L
    }

    override fun existById(id: Long): Boolean {
        return id != FAIL_ID
    }

}