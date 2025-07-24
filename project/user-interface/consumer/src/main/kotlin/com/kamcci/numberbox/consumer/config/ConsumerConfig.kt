package com.kamcci.numberbox.consumer.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
    value = [ConsumerProperty::class]
)
open class ConsumerConfig