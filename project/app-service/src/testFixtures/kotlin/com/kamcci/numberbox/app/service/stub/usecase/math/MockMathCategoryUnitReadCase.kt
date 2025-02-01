package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathCategoryUnitVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase

class MockMathCategoryUnitReadCase : MathCategoryUnitReadCase {
    override fun readAll(): List<MathCategoryUnitVo> {
        return getMathCategoryUnitVo()
    }
}