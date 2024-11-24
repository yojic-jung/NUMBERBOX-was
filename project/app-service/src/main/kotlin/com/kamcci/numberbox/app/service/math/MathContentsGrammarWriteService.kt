package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsGrammarWriteOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteUseCase

@UseCase
class MathContentsGrammarWriteService(
    private val mathConGrammarModifyOrmPort: MathContentsGrammarWriteOrmPort
) : MathContentsGrammarWriteUseCase {

    @TXExecute
    override fun createGrammar(contentsId: Long, grammar: String) {
        mathConGrammarModifyOrmPort.createGrammar(contentsId, grammar)
    }
}