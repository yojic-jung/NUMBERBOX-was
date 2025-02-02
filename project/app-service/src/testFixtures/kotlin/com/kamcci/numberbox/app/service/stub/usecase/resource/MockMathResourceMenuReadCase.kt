package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceMenuVoList
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase

class MockMathResourceMenuReadCase : MathResourceMenuReadCase {
    override fun readAll(): List<MathResourceMenuVo> {
        return getMathResourceMenuVoList()
    }
}