package com.kamcci.numberbox.app.service.stub.usecase.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase

class MockMathResourceMenuReadCase : MathResourceMenuReadCase {
    override fun readAll(): List<MathResourceMenuVo> {
        TODO("Not yet implemented")
    }
}