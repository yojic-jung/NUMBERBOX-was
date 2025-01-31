package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import java.util.*

class MockMathDocsUsageWriteCase : MathDocsUsageWriteCase {
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        TODO("Not yet implemented")
    }

}