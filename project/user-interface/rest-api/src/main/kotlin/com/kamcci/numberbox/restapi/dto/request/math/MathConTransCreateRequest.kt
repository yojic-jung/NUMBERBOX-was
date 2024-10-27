package com.kamcci.numberbox.restapi.dto.request.math

import jakarta.validation.Valid

/**
 * 변형문제 생성 request
 */
data class MathConTransCreateRequest(
    // 변형문제의 경우 원본 문제 id
    val orgContentsId: Long,
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
)