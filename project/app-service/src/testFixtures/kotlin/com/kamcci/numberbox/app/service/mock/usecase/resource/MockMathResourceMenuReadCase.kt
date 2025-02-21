package com.kamcci.numberbox.app.service.mock.usecase.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.service.sample.MathResourceSampleData.getMathResourceMenuVoList
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase

class MockMathResourceMenuReadCase : MathResourceMenuReadCase {
    override fun readAll(): List<MathResourceMenuVo> {
        return getMathResourceMenuVoList()
    }
}