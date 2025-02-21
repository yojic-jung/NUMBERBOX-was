package com.kamcci.numberbox.app.service.mock.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMathDocsUsageWriteOrmPort : MathDocsUsageWriteOrmPort {
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        return if (memberId == FAIL_MEMBER_ID) return 0L else 1L
    }
}