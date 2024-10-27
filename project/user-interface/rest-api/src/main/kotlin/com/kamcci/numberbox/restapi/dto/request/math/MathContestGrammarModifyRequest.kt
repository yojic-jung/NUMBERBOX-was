package com.kamcci.numberbox.restapi.dto.request.math

import jakarta.validation.constraints.NotEmpty


/**
 * 수학문제 문법 등록
 */
data class MathContestGrammarModifyRequest(
    val contentsId: Long,
    // 문법
    @field:NotEmpty
    val grammar: String,
)