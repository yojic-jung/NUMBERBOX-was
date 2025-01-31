package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import java.util.*

class MockMathResourceWriteCase : MathResourceWriteCase {
    override fun create(createDto: MathResourceCreateDto): Long {
        TODO("Not yet implemented")
    }

    override fun update(updateDto: MathResourceUpdateDto) {
        TODO("Not yet implemented")
    }

    override fun deleteByIdAndMemberId(id: Long, memberId: UUID) {
        TODO("Not yet implemented")
    }
}