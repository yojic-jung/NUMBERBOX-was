package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import java.util.*

class MockMathContentsRepoReadCase : MathContentsRepoReadCase {
    override fun readContentsIdByMemberId(memberId: UUID): List<Long> {
        return listOf(1L, 2L)
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return contentsId != FAIL_ID
    }
}