package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathCategoryUnitVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase

class MockMathCategoryUnitReadCase : MathCategoryUnitReadCase {
    override fun readAll(): List<MathCategoryUnitVo> {
        return getMathCategoryUnitVo()
    }
}