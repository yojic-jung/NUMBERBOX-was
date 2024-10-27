package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import jakarta.validation.Valid

/**
 * 입시 수학문제 출저 정보 생성 request
 */
data class MathConIpsiSrcCreateRequest(
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
    // 입시 문제 출처 정보
    val ipsiSrc: MathConIpsiSrcModifyDto
)