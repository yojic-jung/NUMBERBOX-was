package com.kamcci.numberbox.app.service.mock.port.orm.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.port.orm.resource.MathResourceWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMathResourceWriteOrmPort : MathResourceWriteOrmPort {
    override fun create(createDto: MathResourceCreateDto): Long {
        return if (createDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun update(updateDto: MathResourceUpdateDto) {
    }

    override fun deleteByIdAndMemberId(id: Long, memberId: UUID): Long {
        return if (id == FAIL_ID) 0L else 1L
    }
}