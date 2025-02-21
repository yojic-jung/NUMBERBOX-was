package com.kamcci.numberbox.app.service.mock.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import java.util.*

class MockMathDocsPaperWriteCase : MathDocsPaperWriteCase {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        return when {
            memberId == FAIL_MEMBER_ID -> 0L

            memberId == EXCEPTION_MEMBER_ID -> throw RuntimeException(STUB_EXCEPTION_MSG)

            else -> 1L
        }
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto) {
        if (memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun delete(docsId: Long, memberId: UUID) {
        if (docsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}