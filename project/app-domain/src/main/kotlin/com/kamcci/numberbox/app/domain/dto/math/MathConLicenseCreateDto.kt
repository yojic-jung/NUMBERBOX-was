package com.kamcci.numberbox.app.domain.dto.math

/**
 * 사용자 수학문제 저작권 정보 생성 dto
 */
data class MathConLicenseCreateDto(
    // 공유 여부
    val shareStts: Boolean,
    // 온라인 공유 여부
    val onlineLicStts: Boolean,
    // 개인 대상 공유 여부
    val perLicStts: Boolean,
    // 기업 대상 공유 여부
    val entLicStts: Boolean,
)