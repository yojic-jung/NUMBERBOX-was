package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import java.util.*

class MockMathContentsLikeReadCase : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        TODO("Not yet implemented")
    }
}