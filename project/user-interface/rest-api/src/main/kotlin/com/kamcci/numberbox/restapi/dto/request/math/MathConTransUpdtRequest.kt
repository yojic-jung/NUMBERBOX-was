package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.restapi.validation.math.ContentsCheck
import jakarta.validation.Valid

/**
 * 변형문제 생성 request
 */
data class MathConTransUpdtRequest(
    @field:ContentsCheck
    val contentsId: Long,
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
)