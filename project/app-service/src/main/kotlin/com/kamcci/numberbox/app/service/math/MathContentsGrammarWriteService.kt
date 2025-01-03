//package com.kamcci.numberbox.app.service.math
//
//import com.kamcci.numberbox.app.domain.system_construction.UseCase
//import com.kamcci.numberbox.app.port.orm.math.MathContentsGrammarWriteOrmPort
//import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteCase
//
//@UseCase
//class MathContentsGrammarWriteService(
//    private val mathConGrammarModifyOrmPort: MathContentsGrammarWriteOrmPort
//) : MathContentsGrammarWriteCase {
//
//    override fun createGrammar(contentsId: Long, grammar: String): Boolean {
//        return mathConGrammarModifyOrmPort.createGrammar(contentsId, grammar)
//    }
//}