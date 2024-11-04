package com.kamcci.numberbox.app.domain.dto.docs

/**
 * 입시 수학문제 검색 쿼리
 */
data class MathIpsiDocsReadDto(
    // 단원id+유형id ('단원id-유형id' 형식)
    val unitIdAndTypeId: String,
    // 난이도
    val quesLevel: Int,
    // 오답률 최소(포함)
    val wrongRatioMin: Int,
    // 오답률 최대(포함)
    val wrongRatioMax: Int,
    // 출제년도 시작(포함)
    val ipsiYearStrt: Int,
    // 출제년도 종료(포함)
    val ipsiYearEnd: Int,
    // 문제 갯수
    val count: Int,
) {
    init {
        require(quesLevel in 1..5) { "수학문제 난이도는 1에서 5 이하입니다." }
        require(wrongRatioMin in 0..100 && wrongRatioMax in 0..100 && wrongRatioMin <= wrongRatioMax)
        { "오답률은 0에서 100 사이 값만 가능합니다." }
        require(count in 1..100) { "수학문제는 최소 5문제 이상 100문제 이하" }

    }
}