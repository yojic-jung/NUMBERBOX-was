package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_ID
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import java.util.*

class MockMathContentsLikeReadCase : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return contentsId != FAIL_ID
    }
}