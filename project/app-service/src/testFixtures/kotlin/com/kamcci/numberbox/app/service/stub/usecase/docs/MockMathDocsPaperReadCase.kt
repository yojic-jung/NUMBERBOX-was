package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import java.util.*

class MockMathDocsPaperReadCase : MathDocsPaperReadCase {
    override fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo? {
        TODO("Not yet implemented")
    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathDocsPaperVo> {
        TODO("Not yet implemented")
    }

    override fun countByMemberId(memberId: UUID): Long {
        TODO("Not yet implemented")
    }
}