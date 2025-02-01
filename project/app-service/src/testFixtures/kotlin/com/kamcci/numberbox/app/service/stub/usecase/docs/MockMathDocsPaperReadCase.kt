package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_ID
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsPaperVo
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsPaperVoList
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import java.util.*

class MockMathDocsPaperReadCase : MathDocsPaperReadCase {
    override fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo? {
        return if (id == FAIL_ID) {
            null
        } else {
            getMathDocsPaperVo()
        }
    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathDocsPaperVo> {
        return getMathDocsPaperVoList(pageReq.pageVolume)
    }

    override fun countByMemberId(memberId: UUID): Long {
        return 10L
    }
}