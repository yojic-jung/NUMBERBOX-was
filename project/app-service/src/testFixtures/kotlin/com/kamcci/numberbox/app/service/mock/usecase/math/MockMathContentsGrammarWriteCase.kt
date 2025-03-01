package com.kamcci.numberbox.app.service.mock.usecase.math

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteCase

class MockMathContentsGrammarWriteCase : MathContentsGrammarWriteCase {
    override fun update(contentsId: Long, grammar: String): Boolean {
        return contentsId != FAIL_ID
    }
}