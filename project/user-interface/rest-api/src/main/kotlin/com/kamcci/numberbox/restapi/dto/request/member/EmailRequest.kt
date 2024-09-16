package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.EmailCheck

data class EmailRequest(
    @field:EmailCheck
    val email: String,
)