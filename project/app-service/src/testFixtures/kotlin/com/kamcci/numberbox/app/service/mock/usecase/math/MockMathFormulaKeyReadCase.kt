package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathFormulaKeyVoList
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadCase

class MockMathFormulaKeyReadCase : MathFormulaKeyReadCase {
    override fun readAll(): List<MathFormulaKeyVo> {
        return getMathFormulaKeyVoList()
    }
}