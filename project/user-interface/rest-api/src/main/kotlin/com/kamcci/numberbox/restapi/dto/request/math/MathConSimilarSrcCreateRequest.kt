package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import jakarta.validation.Valid

/**
 * 자체제작 수학문제 생성 request
 */
data class MathConSimilarSrcCreateRequest(
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
    val similarSrc: MathConSimilarSrcCreateDto
)