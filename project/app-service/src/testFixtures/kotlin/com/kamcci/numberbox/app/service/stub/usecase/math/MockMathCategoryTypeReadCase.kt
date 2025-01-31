package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadCase

class MockMathCategoryTypeReadCase : MathCategoryTypeReadCase {
    override fun readByUnitId(unitId: Int): List<MathCategoryTypeVo> {
        TODO("Not yet implemented")
    }

    override fun readByUnitId(unitIdList: List<Int>): List<MathCategoryTypeVo> {
        TODO("Not yet implemented")
    }
}