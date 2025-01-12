package com.kamcci.numberbox.hwp.client.engine.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
    value = [HwpSocketClientProperty::class]
)
open class HwpSocketClientConfig