package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.restapi.validation.math.ContentsCheck
import jakarta.validation.Valid

/**
 * 수학문제 저작권 정보 수정 request
 */
data class MathConLicenseUpdtRequest(
    @field:ContentsCheck
    val contentsId: Long,
    // 수학문제
    @Valid
    val contents: MathContentsModifyRequest,
    // 저작권 정보
    val license: MathConLicenseModifyDto,
)