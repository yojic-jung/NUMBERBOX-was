package com.kamcci.numberbox.hwp.client.adapter.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
    value = [HwpSocketClientProperty::class]
)
class HwpSocketClientConfig