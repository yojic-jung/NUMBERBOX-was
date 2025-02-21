package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoWriteCase

class MockMathContentsRepoWriteCase : MathContentsRepoWriteCase {
    override fun save(modifyDto: MathContentsRepoModifyDto) {
        if (modifyDto.contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto) {
        if (modifyDto.contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}