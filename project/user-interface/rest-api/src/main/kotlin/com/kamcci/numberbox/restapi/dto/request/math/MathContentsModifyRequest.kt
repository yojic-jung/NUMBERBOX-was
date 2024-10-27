package com.kamcci.numberbox.restapi.dto.request.math

/**
 * 수학문제 생성 및 수정 request
 */
data class MathContentsModifyRequest(
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 수학 문제
    val contents: String,
    // 해설
    val solution: String?,
    // 주관식 정답
    val answer: String?,
    // 객관식 정답
    val choiceAnswer: List<String>?,
    // 객관식 1번
    val firNo: String?,
    // 객관식 2번
    val secNo: String?,
    // 객관식 3번
    val thrNo: String?,
    // 객관식 4번
    val fourNo: String?,
    // 객관식 5번
    val fifNo: String?,
    // 난이도
    val quesLevel: Int,
) {
    companion object {
        // 객관식 정답 가능한 값
        val choiceAnswerValues = listOf("①", "②", "③", "④", "⑤")
    }

    init {
        // 객관식 정답 가능 값
        choiceAnswer?.forEach {
            require(choiceAnswerValues.contains(it))
        }

        // 문제 난이도는 1부터 5까지
        require(quesLevel in 1..5)
    }
}