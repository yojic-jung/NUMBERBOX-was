package com.kamcci.modules.identity.verify.iamport.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "identity.verification.iamport.merchant",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class IamportMerchantProperty(
    val uid: String,
    val idCode: String,
)