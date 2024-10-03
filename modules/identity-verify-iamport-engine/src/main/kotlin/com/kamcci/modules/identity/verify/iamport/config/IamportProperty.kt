package com.kamcci.modules.identity.verify.iamport.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "identity.verification.iamport",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class IamportProperty(
    val apiUrl: String,
    val apiKey: String,
    val apiSecretKey: String,
)