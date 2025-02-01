package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathCategoryTypeVoList
import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadCase

class MockMathCategoryTypeReadCase : MathCategoryTypeReadCase {
    override fun readByUnitId(unitId: Int): List<MathCategoryTypeVo> {
        return getMathCategoryTypeVoList()
    }

    override fun readByUnitId(unitIdList: List<Int>): List<MathCategoryTypeVo> {
        return getMathCategoryTypeVoList()
    }
}