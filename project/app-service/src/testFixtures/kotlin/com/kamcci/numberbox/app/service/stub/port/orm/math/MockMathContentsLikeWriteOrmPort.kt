package com.kamcci.numberbox.app.service.stub.port.orm.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID

class MockMathContentsLikeWriteOrmPort : MathContentsLikeWriteOrmPort {
    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        return modifyDto.contentsId != FAIL_ID
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        return if (modifyDto.contentsId == FAIL_ID) 0L else 1L
    }
}