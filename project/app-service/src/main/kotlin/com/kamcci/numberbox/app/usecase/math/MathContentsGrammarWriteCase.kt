package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.system_construction.TXExecute

/**
 * 수학문제 문법 저장
 */
interface MathContentsGrammarWriteCase {
    /**
     * 수학문제 tex 문법으로 변환 형식 저장
     *
     * @return
     * true : 수정
     * false : 신규
     */
    @TXExecute
    fun createGrammar(contentsId: Long, grammar: String): Boolean

}