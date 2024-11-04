package com.kamcci.numberbox.app.domain.dto.docs

/**
 * 자체제작 수학문제 검색 쿼리
 */
data class MathInHouseDocsReadDto(
    // 단원id+유형id ('단원id-유형id' 형식)
    val unitIdAndTypeId: String,
    // 난이도
    val quesLevel: Int,
    // 문제 갯수
    val count: Int,
) {
    init {
        require(quesLevel in 1..5) { "수학문제 난이도는 1에서 5 이하입니다." }
        require(count in 1..100) { "수학문제는 최소 5문제 이상 100문제 이하" }
    }
}