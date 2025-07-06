package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import java.util.*

class MockMathContentsLikeReadCase : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return contentsId == EXIST_ID
    }

    override fun countBy(contentsId: Long): Long {
        return 1L
    }
}