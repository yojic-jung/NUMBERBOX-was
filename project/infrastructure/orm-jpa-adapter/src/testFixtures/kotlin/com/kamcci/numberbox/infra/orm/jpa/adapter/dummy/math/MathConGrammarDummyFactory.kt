package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math

/**
 * MathConGrammarEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathConGrammarDummyFactory {

    const val NOT_SAVED_CONTENTS_ID = 4907L

    fun getMathConGrammarDummyEntity() = ExistEntityInfo(1)

    class ExistEntityInfo(val contentsId: Long)
}