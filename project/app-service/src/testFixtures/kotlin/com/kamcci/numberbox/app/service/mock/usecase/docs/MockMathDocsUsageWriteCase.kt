package com.kamcci.numberbox.app.service.mock.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import java.util.*

class MockMathDocsUsageWriteCase : MathDocsUsageWriteCase {
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        return when {
            memberId == FAIL_MEMBER_ID -> 0L

            memberId == EXCEPTION_MEMBER_ID -> throw RuntimeException(STUB_EXCEPTION_MSG)

            else -> 1L
        }
    }

}