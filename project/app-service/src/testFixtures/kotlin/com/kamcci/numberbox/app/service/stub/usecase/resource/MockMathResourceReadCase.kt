package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceDetailVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceFileVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceDetailVo
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceDetailVoList
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceFileVo
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceVoList
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import java.util.*

class MockMathResourceReadCase : MathResourceReadCase {
    override fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo> {
        return getMathResourceVoList()
    }

    override fun countByMainCateId(mainCateId: Int): Long {
        return 10L
    }

    override fun readById(id: Long): MathResourceDetailVo {
        return getMathResourceDetailVo()
    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathResourceDetailVo> {
        return getMathResourceDetailVoList()
    }

    override fun countByMemberId(memberId: UUID): Long {
        return 10L
    }

    override fun readFileById(resourceId: Long): MathResourceFileVo {
        return getMathResourceFileVo()
    }
}