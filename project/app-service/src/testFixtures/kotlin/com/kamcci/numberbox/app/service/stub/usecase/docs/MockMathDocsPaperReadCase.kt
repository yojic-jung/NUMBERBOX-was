package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.service.constant.MockTestConstant
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsPaperVo
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsPaperVoList
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import java.util.*

class MockMathDocsPaperReadCase : MathDocsPaperReadCase {
    override fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo? {
        return when {
            id == FAIL_ID -> null

            id == EXCEPTION_ID -> throw RuntimeException(STUB_EXCEPTION_MSG)
            
            else -> getMathDocsPaperVo()
        }
    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathDocsPaperVo> {
        return getMathDocsPaperVoList(pageReq.pageVolume)
    }

    override fun countByMemberId(memberId: UUID): Long {
        return 10L
    }
}