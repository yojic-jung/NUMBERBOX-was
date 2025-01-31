package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteCase

class MockMathContentsGrammarWriteCase : MathContentsGrammarWriteCase {
    override fun createGrammar(contentsId: Long, grammar: String): Boolean {
        TODO("Not yet implemented")
    }
}