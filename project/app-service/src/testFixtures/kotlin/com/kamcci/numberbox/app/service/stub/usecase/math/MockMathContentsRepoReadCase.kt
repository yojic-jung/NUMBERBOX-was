package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import java.util.*

class MockMathContentsRepoReadCase : MathContentsRepoReadCase {
    override fun readContentsIdByMemberId(memberId: UUID): List<Long> {
        TODO("Not yet implemented")
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        TODO("Not yet implemented")
    }
}