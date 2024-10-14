package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.restapi.validation.math.ContentsCheck

data class ContentsIdRequest(
    // 수학 문제 컨텐츠 id
    @field:ContentsCheck
    val contentsId: Long
)