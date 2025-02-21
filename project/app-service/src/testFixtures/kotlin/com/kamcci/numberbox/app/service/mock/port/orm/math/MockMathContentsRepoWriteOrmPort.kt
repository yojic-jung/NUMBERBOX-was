package com.kamcci.numberbox.app.service.mock.port.orm.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID

class MockMathContentsRepoWriteOrmPort : MathContentsRepoWriteOrmPort {
    override fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        return modifyDto.contentsId != FAIL_ID
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        return if (modifyDto.contentsId == FAIL_ID) 0L else 1L
    }

}