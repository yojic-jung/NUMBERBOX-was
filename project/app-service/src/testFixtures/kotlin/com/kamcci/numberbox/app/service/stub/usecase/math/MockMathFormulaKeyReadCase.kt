package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathFormulaKeyVoList
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadCase

class MockMathFormulaKeyReadCase : MathFormulaKeyReadCase {
    override fun readAll(): List<MathFormulaKeyVo> {
        return getMathFormulaKeyVoList()
    }
}