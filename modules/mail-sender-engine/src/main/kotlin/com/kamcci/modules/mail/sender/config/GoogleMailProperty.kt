package com.kamcci.modules.mail.sender.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "mail.sender.google.server",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class GoogleMailProperty(
    val host: String,
    val port: String,
    val auth: String,
    val sslEnable: String,
    val sslTrust: String,
    val protocols: String
)