package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import java.util.*

class MockMathResourceWriteCase : MathResourceWriteCase {
    override fun create(createDto: MathResourceCreateDto): Long {
        return if (createDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun update(updateDto: MathResourceUpdateDto) {
        if (updateDto.resourceId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun deleteByIdAndMemberId(id: Long, memberId: UUID) {
        if (id == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}