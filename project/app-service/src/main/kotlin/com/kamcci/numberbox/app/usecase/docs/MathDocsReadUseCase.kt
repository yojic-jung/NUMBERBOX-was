package com.kamcci.numberbox.app.usecase.docs

/**
 * 학습지 제작
 */
interface MathDocsReadUseCase {

    // 자체제작 수학문제 학습지 제작
    fun makeInHouseDocs(unitIdAndTypeId: List<String>, count: Int, quesLevel: Int): List<Any>

    // 입시 수학문제 제작

}