package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.EmailChecker

data class EmailRequest(
    @field:EmailChecker
    val email: String,
)