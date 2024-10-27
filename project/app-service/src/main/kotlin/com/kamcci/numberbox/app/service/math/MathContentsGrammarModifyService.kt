package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathContentsGrammarModifyOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarModifyUseCase

@UseCase
class MathContentsGrammarModifyService(
    private val mathConGrammarModifyOrmPort: MathContentsGrammarModifyOrmPort
) : MathContentsGrammarModifyUseCase {

    @TXExecute
    override fun createGrammar(contentsId: Long, grammar: String) {
        mathConGrammarModifyOrmPort.createGrammar(contentsId, grammar)
    }
}