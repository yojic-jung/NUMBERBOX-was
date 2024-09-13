package com.kamcci.numberbox.restapi.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.kamcci.numberbox.restapi.validation.member.EmailChecker

data class EmailRequest(
    @JsonProperty("email")
    @field:EmailChecker
    val email: String
)