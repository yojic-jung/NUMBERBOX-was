package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.usecase.math.MathContentsIpsiReadCase

class MockMathContentsIpsiReadCase : MathContentsIpsiReadCase {
    override fun readAllIpsiYear(): List<Int> {
        return (2009..2025).toList()
    }
}