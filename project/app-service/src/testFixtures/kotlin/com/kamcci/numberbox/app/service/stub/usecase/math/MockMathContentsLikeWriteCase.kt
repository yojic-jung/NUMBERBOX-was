package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_ID
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeWriteCase

class MockMathContentsLikeWriteCase : MathContentsLikeWriteCase {
    override fun save(modifyDto: MathContentsLikeModifyDto) {
        if (modifyDto.contentsId == FAIL_ID) throw RuntimeException()
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto) {
        if (modifyDto.contentsId == FAIL_ID) throw RuntimeException()
    }
}