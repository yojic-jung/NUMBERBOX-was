package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import jakarta.validation.Valid

/**
 * 수학문제 저작권 정보 생성 request
 */
data class MathConLicenseCreateRequest(
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
    // 저작권 정보
    val license: MathConLicenseModifyDto,
)