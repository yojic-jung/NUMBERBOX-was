package com.kamcci.numberbox.restapi.dto.request.math

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

/**
 * 수학문제 검색 조건 request
 */
data class MathContentsSearchRequest(
    // 검색 조건
    val searchType: SearchType,
    // 단원 id
    val unitId: Int,
    @field:PositiveOrZero(message = "페이지 번호는 0 이상 이어야 합니다.")
    val pageNum: Long = 0,
    @field:Positive(message = "페이지 볼륨은 0보다 커야 합니다.")
    val pageVolume: Long = 100,
) {
    enum class SearchType {
        Subject,
        FirUnit,
        SecUnit,
        ThrUnit
    }
}