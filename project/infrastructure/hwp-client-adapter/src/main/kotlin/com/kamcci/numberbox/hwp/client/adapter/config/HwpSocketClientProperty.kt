package com.kamcci.numberbox.hwp.client.adapter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "hwp.server",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class HwpSocketClientProperty(
    val ip: String,
    val port: Int
)