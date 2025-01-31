package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceDetailVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceFileVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import java.util.*

class MockMathResourceReadCase : MathResourceReadCase {
    override fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo> {
        TODO("Not yet implemented")
    }

    override fun countByMainCateId(mainCateId: Int): Long {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): MathResourceDetailVo {
        TODO("Not yet implemented")
    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathResourceDetailVo> {
        TODO("Not yet implemented")
    }

    override fun countByMemberId(memberId: UUID): Long {
        TODO("Not yet implemented")
    }

    override fun readFileById(resourceId: Long): MathResourceFileVo {
        TODO("Not yet implemented")
    }
}