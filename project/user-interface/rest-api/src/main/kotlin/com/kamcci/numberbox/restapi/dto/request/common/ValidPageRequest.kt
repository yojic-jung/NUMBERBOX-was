package com.kamcci.numberbox.restapi.dto.request.common

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class ValidPageRequest(
    @field:PositiveOrZero(message = "페이지 번호는 0 이상 이어야 합니다.")
    val pageNum: Long? = 0,
    @field:Positive(message = "페이지 볼륨은 0보다 커야 합니다.")
    val pageVolume: Long? = 100,
)