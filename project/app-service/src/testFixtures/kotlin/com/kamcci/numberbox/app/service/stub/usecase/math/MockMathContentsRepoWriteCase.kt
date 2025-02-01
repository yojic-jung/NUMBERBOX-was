package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.service.constant.FailConstant
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoWriteCase

class MockMathContentsRepoWriteCase : MathContentsRepoWriteCase {
    override fun save(modifyDto: MathContentsRepoModifyDto) {
        if (modifyDto.contentsId == FailConstant.FAIL_ID) throw RuntimeException()
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto) {
    }
}