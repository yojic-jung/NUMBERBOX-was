package com.kamcci.modules.mail.sender.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "mail.sender.google.account",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class GoogleAccountProperty(
    val email: String,
    val password: String,
)