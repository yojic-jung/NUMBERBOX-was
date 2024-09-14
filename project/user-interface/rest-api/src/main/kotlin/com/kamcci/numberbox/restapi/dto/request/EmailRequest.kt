package com.kamcci.numberbox.restapi.dto.request

import com.kamcci.numberbox.restapi.validation.member.EmailChecker

data class EmailRequest(
    @field:EmailChecker
    val email: String,
)