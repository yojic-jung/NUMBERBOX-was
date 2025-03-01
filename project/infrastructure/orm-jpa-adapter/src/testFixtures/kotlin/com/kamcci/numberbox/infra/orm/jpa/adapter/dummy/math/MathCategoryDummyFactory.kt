package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math

/**
 * MathCategoryUnitEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathCategoryDummyFactory {
    fun getMathCateUnitIdList() = listOf(
        21001..21030,
        22001..22027,
        23001..23024,
        30001..30017,
        31001..31006,
        32001..32006,
        33001..33006,
        34001..34007,
        35001..35005
    ).flatMap { it.toList() }
}