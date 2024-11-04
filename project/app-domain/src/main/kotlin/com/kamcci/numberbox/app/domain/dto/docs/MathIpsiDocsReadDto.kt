package com.kamcci.numberbox.app.domain.dto.docs

/**
 * 입시 수학문제 검색 쿼리
 */
data class MathIpsiDocsReadDto(
    // 단원id+유형id ('단원id-유형id' 형식)
    val unitIdAndTypeId: List<String>,
    // 배점(2점 : 3, 3점 : 4, 4점 : 5)
    val quesLevel: List<Int>,
    // 오답률 최소(포함)
    val wrongRatioMin: Int,
    // 오답률 최대(포함)
    val wrongRatioMax: Int,
    // 출제년도 시작(포함)
    val ipsiYearStrt: Int,
    // 출제년도 종료(포함)
    val ipsiYearEnd: Int,
    // 출제 월
    val ipsiMonth: List<Int>,
    // 문제 갯수
    val count: Long,
) {
    companion object {
        private val availIpsiMonth = listOf(6, 9, 11)
    }

    init {
        for (quesLv in quesLevel) require(quesLv in 3..5) { "입시문제 배점은 2점(3), 3점(4), 4점(5)만 가능합니다." }

        require(wrongRatioMin in 0..100 && wrongRatioMax in 0..100 && wrongRatioMin <= wrongRatioMax)
        { "오답률은 0에서 100 사이 값만 가능합니다." }

        for (month in ipsiMonth) require(month in availIpsiMonth) { "출제 월은 6, 9, 11월만 가능합니다." }

        require(count in 1..100) { "수학문제는 최소 5문제 이상 100문제 이하" }
    }
}