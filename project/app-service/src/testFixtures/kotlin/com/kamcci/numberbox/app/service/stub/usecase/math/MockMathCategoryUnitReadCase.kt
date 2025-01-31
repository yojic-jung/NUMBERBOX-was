package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase

class MockMathCategoryUnitReadCase : MathCategoryUnitReadCase {
    override fun readAll(): List<MathCategoryUnitVo> {
        TODO("Not yet implemented")
    }
}