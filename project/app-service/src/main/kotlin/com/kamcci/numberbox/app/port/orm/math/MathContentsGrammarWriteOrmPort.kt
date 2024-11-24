package com.kamcci.numberbox.app.port.orm.math

/**
 * 수학문제 문법 저장
 */
interface MathContentsGrammarWriteOrmPort {
    // 수학문제 tex 문법으로 변환 형식 저장
    fun createGrammar(contentsId: Long, grammar: String)
}